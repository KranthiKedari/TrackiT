package com.kk.trackit.controller;

import com.kk.trackit.dao.HierarchyDAO;
import com.kk.trackit.dao.WifiFlagDao;
import com.kk.trackit.db.mongo.TrackItDAO;
import com.kk.trackit.dto.UserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by kkedari on 8/24/15.
 */
@RestController
public class WifiController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/wifi/settings/get/{name}/{flag}", method = RequestMethod.GET)
    public String getUserSettings(@PathVariable("name") String name, @PathVariable("flag") String flag) throws IOException {

        return new WifiFlagDao(jdbcTemplate).getFlag(name, flag);
    }

    @RequestMapping(value = "/wifi/settings/get/{name}", method = RequestMethod.GET)
    public String getUserSettings(@PathVariable("name") String name) throws IOException {

        return new WifiFlagDao(jdbcTemplate).getFlag(name, "");
    }



    @RequestMapping(value = "/wifi/settings/update/{name}/{flag}", method = RequestMethod.POST)
    public String updateUserSettings(@PathVariable("name") String name, @PathVariable("flag") String flag, @RequestParam("value") String value) throws IOException {

        return new WifiFlagDao(jdbcTemplate).updateFlag(name, flag, value);
    }

}
