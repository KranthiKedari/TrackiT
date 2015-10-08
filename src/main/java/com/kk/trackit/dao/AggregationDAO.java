package com.kk.trackit.dao;

import com.kk.trackit.dto.aggregations.AggregationDTO;
import com.kk.trackit.dto.aggregations.GraphElement;
import com.kk.trackit.dto.aggregations.SeriesDTO;
import com.kk.trackit.hicharts.Series;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kkedari on 8/13/15.
 */
public class AggregationDAO {

    private JdbcTemplate jdbcTemplate;

    public AggregationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Series> getAggregationData(String userId, AggregationDTO aggregationDTO, GraphElement graphElement) {
        return getAggregationDataForOneElement(userId, aggregationDTO, graphElement);
    }

    private List<Series> getAggregationDataForOneElement(String userId, AggregationDTO aggregationDTO, GraphElement graphElement) {
        StringBuilder selectClause = new StringBuilder("SELECT ");
        StringBuilder fromClause = new StringBuilder(" FROM ");
        StringBuilder whereClause = new StringBuilder(" WHERE ");
        StringBuilder groupByClause = new StringBuilder(" GROUP BY ");
        StringBuilder outerSelect = new StringBuilder("SELECT " );


        String intervalType = aggregationDTO.getIntervalType();
        Double interval = aggregationDTO.getInterval();

        List<Object> args = new ArrayList<Object>();


        String title = !graphElement.getPath().equals("") ? graphElement.getPath() + "|" + graphElement.getName() : graphElement.getName() ;


        selectClause.append(" user_data.id as id," )
               .append( " user_data.name as name, user_values.name as field_name,user_data.last_updated as last_updated");
        selectClause.append(",user_values.numeric_value as value");
        fromClause.append("  trackit_user_data user_data,trackit_user_values user_values ");

        boolean includeChildren = false;
        whereClause.append(" user_data.user_id = ?  and ");
        args.add(userId);

        if(aggregationDTO.getFlags().containsKey("includeChildren") ) {
            whereClause.append("(user_data.path like ?  or (user_data.path = ? and user_data.name = ? ))");
            args.add(title + "%");
            args.add(graphElement.getPath());
            args.add(graphElement.getName());
        } else {
            whereClause.append(" user_data.path = ? and user_data.name = ? ");
            args.add(graphElement.getPath());
            args.add(graphElement.getName());
        }

        whereClause.append(" and  user_data.id  = user_values.user_data_id and user_values.name in (");


        for(Map.Entry<String, String> entry: graphElement.getValues().entrySet()) {
            whereClause.append("?,");
            args.add(entry.getKey());
        }

        if(whereClause.length() > 1) {
            whereClause.replace(whereClause.length() - 1, whereClause.length(), "");
        }

        whereClause.append(")");


        if(aggregationDTO.getFromTime() != -1) {
            whereClause .append(" and user_data.last_updated >= ?");
            args.add( new java.sql.Timestamp(aggregationDTO.getFromTime()));
        }

        if(aggregationDTO.getEndTime() != -1) {
            whereClause .append(" and user_data.last_updated <= ?");
            args.add( new java.sql.Timestamp(aggregationDTO.getEndTime()));

        }

        outerSelect.append(" a.name as name, a.field_name as field_name, ");

        int intervalInt = interval.intValue();
        if(intervalType.equalsIgnoreCase("year")) {
            outerSelect.append(" str_to_date( concat( floor(year( last_updated )/").append(intervalInt).append(") * ").append(intervalInt).append(" , '-', 1 , '-', 1 ) , '%Y-%m-%d' ) as timerange ");
        }
        else if(intervalType.equalsIgnoreCase("month")) {
            outerSelect.append(" str_to_date( concat( year( last_updated ) , '-', floor(month( last_updated )/").append(intervalInt).append(") * ").append(intervalInt).append(" , '-', 1 ) , '%Y-%m-%d' ) as timerange ");

        }
        else if(intervalType.equalsIgnoreCase("day")) {
            outerSelect.append(" str_to_date( concat( year( last_updated ) , '-', month( last_updated ), '-', floor(day( last_updated )/").append(intervalInt).append(") * ").append(intervalInt).append(" ) , '%Y-%m-%d' ) as timerange ");
        }

        groupByClause.append(" name, field_name, timerange ");

        String aggregationType = aggregationDTO.getAggregationType();

        if(aggregationType.equalsIgnoreCase("sum")) {
            outerSelect.append(", sum(a.value) as value ");
        } else if(aggregationType.equalsIgnoreCase("sum")) {
            outerSelect.append(", avg(a.value) as value ");
        } else if(aggregationType.equalsIgnoreCase("max")) {
            outerSelect.append(", max(a.value) as value ");
        }else if(aggregationType.equalsIgnoreCase("count")) {
            outerSelect.append(", count(a.value) as value ");
        }

         outerSelect
                 .append(" FROM ")
                 .append(" (")
                    .append(selectClause)
                    .append(fromClause)
                    .append(whereClause)
                    .append(") a ").append(groupByClause);


        SqlRowSet rowset = jdbcTemplate.queryForRowSet(outerSelect.toString(), args.toArray());

        rowset.beforeFirst();
        Map<String, Series> seriesMap = new HashMap<String, Series>();


        while(rowset.next()) {
            String name = rowset.getString("name");
            String fieldName = rowset.getString("field_name");
            Double value = rowset.getDouble("value");
            long timeValue = rowset.getTimestamp("timerange").getTime();

            String seriesName = name + "["+ fieldName+ "]";
            if(!seriesMap.containsKey(seriesName)) {
                Series newSeries = new Series();
                newSeries.setType(graphElement.getType());
                newSeries.setName(seriesName);
                seriesMap.put(seriesName, newSeries);
            }

            List<Object> record = new ArrayList<Object>();
            record.add(timeValue);

            record.add(value);
            Series seriesField =  seriesMap.get(seriesName);
            seriesField.getData().add(record);

        }
        return new ArrayList<Series>(seriesMap.values());

    }
}
