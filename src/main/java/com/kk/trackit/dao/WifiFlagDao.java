package com.kk.trackit.dao;

import com.kk.trackit.db.mongo.TrackItDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kkedari on 8/24/15.
 */
public class WifiFlagDao {

    JdbcTemplate jdbcTemplate;

    public WifiFlagDao( JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public String getFlag(String name,String flag) {
        String sql = "select flag,value from trackit_wifi_flags where name = ? ";

        if(!flag.equalsIgnoreCase("") && flag != null) {
            sql+= " and flag =?" ;
        }

        StringBuilder response = new StringBuilder("");
        List<Object> args = new ArrayList<Object>();
        args.add(name);
        args.add(flag);

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, args.toArray());

        sqlRowSet.beforeFirst();

        while(sqlRowSet.next()) {
            response.append(sqlRowSet.getString("flag")).append("=").append(sqlRowSet.getString("value")).append(",");
        }

        if(response.length() > 0) {
            response.replace(response.length() - 1, response.length(), "");
        }
        return "$#" + response + "#$";

    }


    public String updateFlag(String name,String flag, String value) {
        String sql = "update trackit_wifi_flags set value = ? where name = ? and flag =?" ;


        List<Object> args = new ArrayList<Object>();
        args.add(value);
        args.add(name);
        args.add(flag);

        int resp = jdbcTemplate.update(sql, args.toArray());

        if(resp == 1) {
            return value;
        }

        return "";


    }
}
