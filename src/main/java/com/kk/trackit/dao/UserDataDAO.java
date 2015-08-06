package com.kk.trackit.dao;

import com.kk.trackit.dto.response.GetDataResponseDTO;
import com.kk.trackit.dto.response.SeriesDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kkedari on 8/4/15.
 */
public class UserDataDAO  {

    private JdbcTemplate jdbcTemplate;


    public UserDataDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
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
}
