package com.kk.trackit.dao;

import com.kk.trackit.dto.aggregations.AggregationDTO;
import com.kk.trackit.dto.aggregations.GraphElement;
import com.kk.trackit.dto.request.AggregationRequestDTO;
import com.kk.trackit.dto.request.GetDataDTO;
import com.kk.trackit.dto.response.GetDataResponseDTO;
import com.kk.trackit.dto.aggregations.SeriesDTO;
import com.kk.trackit.hicharts.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.*;

/**
 * Created by kkedari on 8/4/15.
 */
public class UserDataDAO  {

    private JdbcTemplate jdbcTemplate;


    public UserDataDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SeriesDTO> getData(AggregationDTO aggregationDTO) {


        return null;
    }

    public GetDataResponseDTO getData(String path, String name, String userId) {

        String title = !path.equals( "") ? path + "|" + name : name;

        String unit = "$";

        GetDataResponseDTO getDataResponseDTO = new GetDataResponseDTO();

        String query = "select id,name, last_updated, numeric_value from trackit_user_data where user_id = ?  and  (path like ?  or (path = ? and name = ? )) order by last_updated";

        List<Object> args = new ArrayList<Object>();
        args.add(userId);
        //args.add(name);
        args.add(title + "%");

        args.add(path);
        args.add(name);
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(query, args.toArray());

        sqlRowSet.beforeFirst();

        Map<String, List<List<Object>>> fieldMap = new HashMap<String, List<List<Object>>>();
        while(sqlRowSet.next()) {
            List<Object> record = new ArrayList<Object>();
            int id = sqlRowSet.getInt("id");
            String fieldName = sqlRowSet.getString("name");
            record.add(sqlRowSet.getTimestamp("last_updated").getTime());
            record.add(sqlRowSet.getDouble("numeric_value"));
            record.add(id);

            if(!fieldMap.containsKey(fieldName)) {
                fieldMap.put(fieldName, new ArrayList<List<Object>>());
            }
            fieldMap.get(fieldName).add(record);
        }
        getDataResponseDTO.setTitle(title);
        getDataResponseDTO.setUnit(unit);
        getDataResponseDTO.setData(new HashMap<String, List<SeriesDTO>>());
        getDataResponseDTO.getData().put("graph1", new ArrayList<SeriesDTO>());

        for(Map.Entry entry: fieldMap.entrySet()) {
            SeriesDTO seriesDTO = new SeriesDTO();
            seriesDTO.setName((String)entry.getKey());
            seriesDTO.setData((List<List<Object>>)entry.getValue());
            getDataResponseDTO.getData().get("graph1").add(seriesDTO);
        }

        query = "select sum(numeric_value) as total, name as name from trackit_user_data where user_id = ? and  (path like ?  or (path = ? and name = ? )) group by name";

        sqlRowSet = jdbcTemplate.queryForRowSet(query, args.toArray());

        double total = 0;
        List<List<Object>> graph2Data = new ArrayList<List<Object>>();
        while(sqlRowSet.next()) {
            List<Object> record = new ArrayList<Object>();
            String fieldName = sqlRowSet.getString("name");
            record.add(fieldName);
            double fieldTotal = sqlRowSet.getDouble("total");
            total += fieldTotal;
            record.add(fieldTotal);
            graph2Data.add(record);

        }
        getDataResponseDTO.getData().put("graph2", new ArrayList<SeriesDTO>());

        SeriesDTO pieDTO = new SeriesDTO();
        pieDTO.setName(title);
        pieDTO.setData(graph2Data);

        List<SeriesDTO> graph2Series = new ArrayList<SeriesDTO>();
        graph2Series.add(pieDTO);
        getDataResponseDTO.getData().put("graph2", graph2Series);

        getDataResponseDTO.setFields(new HashMap<String, Object>());
        getDataResponseDTO.getFields().put("total", total);
        return getDataResponseDTO;
    }

    public HighChart getAggChart(String userId ,AggregationRequestDTO aggregationRequestDTO) {

        AggregationDTO aggregationDTO = aggregationRequestDTO.getAggregationDTO();
        GraphElement graphElement = aggregationRequestDTO.getGraphElement();


        String path = graphElement.getPath();
        String name = graphElement.getName();

        String title = !path.equals( "") ? path + "|" + name : name;

        String unit = "$";


        HighChart graph1 = new HighChart();
        List<Series> series = new ArrayList<Series>();

        graph1.getChart().setType("area");
        graph1.getTitle().setText(title);
        graph1.getSubtitle().setText("Drag/Pinch to ZOOM");

        graph1.setxAxis(new Axis());

        graph1.getxAxis().setLabels(null);
        graph1.getxAxis().setTitle(null);
        graph1.setyAxis(new ArrayList<Axis>());

        Axis yaxis1 = new Axis();
        yaxis1.getTitle().setText(unit);
        AxisLabel axisLabel = new AxisLabel();
        axisLabel.setX(0);
        axisLabel.setY(0);
        axisLabel.setAlign("left");
        axisLabel.setFormat("{value:.,0f}");
        yaxis1.setLabels(axisLabel);


        graph1.getyAxis().add(yaxis1);

        graph1.getxAxis().setType("datetime");


        graph1.getLegend().setX(null);
        graph1.getLegend().setY(20);
        graph1.getLegend().setBorderWidth(0);
        graph1.getLegend().setFloating(false);
        graph1.setPlotOptions(new ChartGenerator().getPlotOptions("series"));


        List<Series> aggSeriesData = new AggregationDAO(this.jdbcTemplate).getAggregationData(userId, aggregationDTO, graphElement);

        graph1.setSeries(aggSeriesData);
        return graph1;
    }


    public Map<String, HighChart> getType1Charts(String userId ,GetDataDTO getDataDTO) {

        String path = getDataDTO.getPath();
        String name = getDataDTO.getName();
        long fromTime =getDataDTO.getFromTime();
        long toTime = getDataDTO.getToTime();
        String title = !path.equals( "") ? path + "|" + name : name;

        String unit = "$";

        Map<String, HighChart> response = new HashMap<String, HighChart>();
        String query = "select user_data.id as id, user_data.name as name, user_data.last_updated as last_updated, user_values.numeric_value as numeric_value from trackit_user_data user_data, trackit_user_values user_values where user_data.user_id = ?  and  (user_data.path like ?  or (user_data.path = ? and user_data.name = ? )) " +
                " and  user_data.id  = user_values.user_data_id and user_values.name = 'count'";



        List<Object> args = new ArrayList<Object>();
        args.add(userId);
        //args.add(name);
        args.add(title + "%");

        args.add(path);
        args.add(name);


        if(fromTime != -1) {
            query = query + " and user_data.last_updated >= ?";
            args.add( new java.sql.Timestamp(fromTime));
        }

        if(toTime != -1) {
            query = query + " and user_data.last_updated <= ?";
            args.add( new java.sql.Timestamp(toTime));

        }

        query+=" order by user_data.last_updated";

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(query, args.toArray());

        sqlRowSet.beforeFirst();

        Map<String, List<List<Object>>> fieldMap = new HashMap<String, List<List<Object>>>();
        while(sqlRowSet.next()) {
            List<Object> record = new ArrayList<Object>();
            int id = sqlRowSet.getInt("id");
            String fieldName = sqlRowSet.getString("name");
            record.add(sqlRowSet.getTimestamp("last_updated").getTime());
            record.add(sqlRowSet.getDouble("numeric_value"));
            record.add(id);

            if(!fieldMap.containsKey(fieldName)) {
                fieldMap.put(fieldName, new ArrayList<List<Object>>());
            }
            fieldMap.get(fieldName).add(record);
        }

        HighChart graph1 = new HighChart();
        List<Series> series = new ArrayList<Series>();
        for(Map.Entry entry: fieldMap.entrySet()) {

            Series graph1Series = new Series();

            graph1Series.setType("area");
            graph1Series.setName((String) entry.getKey());
            graph1Series.setData((List<List<Object>>)entry.getValue());
            series.add(graph1Series);
        }

        graph1.setSeries(series);

        graph1.getChart().setType("area");
        graph1.getTitle().setText(title);
        graph1.getSubtitle().setText("Drag/Pinch to ZOOM");

        graph1.setxAxis(new Axis());

        graph1.getxAxis().setLabels(null);
        graph1.getxAxis().setTitle(null);
        graph1.setyAxis(new ArrayList<Axis>());

        Axis yaxis1 = new Axis();
        yaxis1.getTitle().setText(unit);
        AxisLabel axisLabel = new AxisLabel();
        axisLabel.setX(0);
        axisLabel.setY(0);
        axisLabel.setAlign("left");
        axisLabel.setFormat("{value:.,0f}");
        yaxis1.setLabels(axisLabel);


        graph1.getyAxis().add(yaxis1);

        graph1.getxAxis().setType("datetime");


        graph1.getLegend().setX(null);
        graph1.getLegend().setY(20);
        graph1.getLegend().setBorderWidth(0);
        graph1.getLegend().setFloating(false);
        //graph1.setxAxis(null);
        //graph1.setyAxis(null);
        //graph1.setLegend(null);
        //graph1.setTooltip(null);
        graph1.setPlotOptions(new ChartGenerator().getPlotOptions("series"));
        response.put("graph1", graph1);



        query = "select sum(user_values.numeric_value) as total, user_data.name as name from trackit_user_data user_data, trackit_user_values user_values where user_data.user_id = ? and  (user_data.path like ?  or (user_data.path = ? and user_data.name = ? ))  and  " +
                " user_data.id  = user_values.user_data_id and user_values.name = 'count'  ";

        if(fromTime != -1) {
            query = query + " and user_data.last_updated >= ?";
        }

        if(toTime != -1) {
            query = query + " and user_data.last_updated <= ?";

        }

        query = query + "group by name";

        sqlRowSet = jdbcTemplate.queryForRowSet(query, args.toArray());

        double total = 0;
        List<List<Object>> graph2Data = new ArrayList<List<Object>>();
        while(sqlRowSet.next()) {
            List<Object> record = new ArrayList<Object>();
            String fieldName = sqlRowSet.getString("name");
            record.add(fieldName);
            double fieldTotal = sqlRowSet.getDouble("total");
            total += fieldTotal;
            record.add(fieldTotal);
            graph2Data.add(record);

        }

        HighChart graph2 = new HighChart();
        series = new ArrayList<Series>();
        Series graph2Series = new Series();

        graph2Series.setType("pie");
        graph2Series.setName(title);
        graph2Series.setData(graph2Data);
        graph2.getTitle().setText(title + "[ TOTAL = " + total + "]");
        series.add(graph2Series);
        graph2.getChart().setOptions3d(new Options3D());
        graph2.setPlotOptions(new ChartGenerator().getPlotOptions("pie"));
        graph2.setSeries(series);
        response.put("graph2", graph2);

        return response;
    }
}
