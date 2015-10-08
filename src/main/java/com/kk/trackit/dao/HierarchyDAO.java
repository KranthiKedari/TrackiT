package com.kk.trackit.dao;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kk.trackit.db.mongo.TrackItDAO;
import com.kk.trackit.dto.Element;
import com.kk.trackit.dto.Jit.JitElement;
import com.kk.trackit.dto.Settings;
import com.kk.trackit.dto.UserSettings;
import com.kk.trackit.dto.hierarchy.Assignments;
import com.kk.trackit.dto.hierarchy.Hierarchy;
import com.kk.trackit.dto.request.AddValueDTO;
import com.kk.trackit.dto.units.UnitConfig;
import com.kk.trackit.util.JitUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kkedari on 8/3/15.
 */
public class HierarchyDAO {

    TrackItDAO trackItDAO;

    JdbcTemplate jdbcTemplate;

    public HierarchyDAO(TrackItDAO trackItDAO, JdbcTemplate jdbcTemplate) {
        this.trackItDAO = trackItDAO;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, JitElement> addGroup(String userId, Element group) {
        UserSettings userSettings = trackItDAO.getCurrentSettings(userId);

        Settings settings = userSettings.getSettings();

        Hierarchy hierarchy = settings.getHierarchy();
        hierarchy.addGroup(group);

        settings.setHierarchy(hierarchy);
        userSettings.setSettings(settings);

        trackItDAO.save(userSettings);


        userSettings = trackItDAO.getCurrentSettings("backup");


        settings.setHierarchy(hierarchy);
        userSettings.setSettings(settings);

        trackItDAO.save(userSettings);
        return new JitUtil().getJitHierarchy(userSettings);
    }

    public Map<String, JitElement> addNode(String userId, Element node, String path) {
        UserSettings userSettings = trackItDAO.getCurrentSettings(userId);

        Settings settings = userSettings.getSettings();

        Hierarchy hierarchy = settings.getHierarchy();
        hierarchy.addChildNode(path, node);

        settings.setHierarchy(hierarchy);
        userSettings.setSettings(settings);

        trackItDAO.save(userSettings);


        userSettings = trackItDAO.getCurrentSettings("backup");


        settings.setHierarchy(hierarchy);
        userSettings.setSettings(settings);

        trackItDAO.save(userSettings);
        return new JitUtil().getJitHierarchy(userSettings);

    }

    public UserSettings getUserSettings(String id) throws IOException {
        UserSettings userSettings = trackItDAO.getCurrentSettings(id);

        if (userSettings == null) {
            InputStream hierachyStream =
                    getClass().getResourceAsStream("/json/hierarchy.json");
            InputStream unitStream =
                    getClass().getResourceAsStream("/json/units.json");
            InputStream assignmentStream =
                    getClass().getResourceAsStream("/json/assignments.json");
            ObjectMapper mapper = new ObjectMapper();
            Hierarchy hierarchy = mapper.readValue(hierachyStream, Hierarchy.class);
            UnitConfig unitConfig = mapper.readValue(unitStream, UnitConfig.class);
            Assignments assignments = mapper.readValue(assignmentStream, Assignments.class);

            Settings defaultSettings = new Settings(hierarchy, unitConfig, assignments);
            userSettings = new UserSettings(id, defaultSettings);

            trackItDAO.save(userSettings);
            userSettings.setUserId("backup");
            userSettings.setId(new ObjectId());
            trackItDAO.save(userSettings);
        }
        return userSettings;
    }

    public int addValue(String userId, AddValueDTO addValueDTO) {

        String path = addValueDTO.getPath();
        String name = addValueDTO.getName();
        String value = addValueDTO.getValue();

        String notes = addValueDTO.getNotes();
                String idQuery = " select  max(id) as id from trackit_user_data";

        SqlRowSet  idRowSet = jdbcTemplate.queryForRowSet(idQuery);

        idRowSet.beforeFirst();
        int id = -1;
        while(idRowSet.next()) {
            id = idRowSet.getInt("id");
        }
        if(id == -1) {
            return 0;
        }

        id = id+1;
        String query = "insert into trackit_user_data(`id`,`user_id`, `name`, `path`, `last_updated`, `notes`) values(?,?,?,?,?, ?)";

        List<Object> args = new ArrayList<Object>();
        args.add(id);
        args.add(userId);
        args.add(name);
        args.add(path);

        if(addValueDTO.getTime() == -1) {
            java.sql.Timestamp sq = new java.sql.Timestamp(new Date().getTime());

            args.add(sq);
        } else {
            args.add(new java.sql.Timestamp(addValueDTO.getTime()));
        }
        args.add(notes);
        int response =  jdbcTemplate.update(query, args.toArray());

        if(response == 1) {
            query = "insert into trackit_user_values(`user_data_id`, `name`, `numeric_value`) values(?,?,?)";
            args = new ArrayList<Object>();
            args.add(id);
            args.add("count");
            args.add(Double.parseDouble(value));
            return jdbcTemplate.update(query, args.toArray());
        }

        return 0;
    }


}
