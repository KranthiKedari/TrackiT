package com.kk.trackit.controller;

import com.kk.trackit.dao.HierarchyDAO;
import com.kk.trackit.dao.UserDataDAO;
import com.kk.trackit.db.mongo.TrackItDAO;
import com.kk.trackit.dto.aggregations.AggregationDTO;
import com.kk.trackit.dto.aggregations.GraphElement;
import com.kk.trackit.dto.request.AddNodeDTO;
import com.kk.trackit.dto.Jit.JitElement;
import com.kk.trackit.dto.UserSettings;
import com.kk.trackit.dto.request.AddValueDTO;
import com.kk.trackit.dto.request.AggregationRequestDTO;
import com.kk.trackit.dto.request.GetDataDTO;
import com.kk.trackit.dto.response.GetDataResponseDTO;
import com.kk.trackit.hicharts.ChartGenerator;
import com.kk.trackit.hicharts.HighChart;
import com.kk.trackit.sms.MessageScanner;
import com.kk.trackit.util.JitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kkedari on 7/10/15.
 */
@RestController
public class TrackItController {

    @Autowired
    private TrackItDAO trackItDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/trackit/get", method = RequestMethod.GET)
    public String getUserData() {
        return new MessageScanner().getMessages();
    }

    @RequestMapping(value = "/trackit/settings/get/{id}", method = RequestMethod.GET)
    public UserSettings getUserSettings(@PathVariable("id") String userId) throws IOException {

        return new HierarchyDAO(trackItDAO, jdbcTemplate).getUserSettings(userId);
    }

    @RequestMapping(value = "/trackit/jit/get/{id}", method = RequestMethod.GET)
    public Map<String, JitElement> getJitSettings(@PathVariable("id") String userId) throws IOException {

        return new JitUtil().getJitHierarchy(new HierarchyDAO(trackItDAO, jdbcTemplate).getUserSettings(userId));
    }

    @RequestMapping(value = "/trackit/addNode/{id}", method = RequestMethod.POST)
    public Map<String, JitElement> addNode(@PathVariable("id") String userId, @RequestBody(required = true)AddNodeDTO addNodeDTO) {
        return new HierarchyDAO(trackItDAO, jdbcTemplate).addNode(userId, addNodeDTO.getNode(), addNodeDTO.getPath());
    }

    @RequestMapping(value = "/trackit/addGroup/{id}", method = RequestMethod.POST)
    public Map<String, JitElement> addGroup(@PathVariable("id") String userId, @RequestBody(required = true)AddNodeDTO addNodeDTO) {
        return new HierarchyDAO(trackItDAO, jdbcTemplate).addGroup(userId, addNodeDTO.getNode());
    }

    @RequestMapping(value = "/trackit/addValue/{id}", method = RequestMethod.POST)
    public int addValue(@PathVariable("id") String userId, @RequestBody(required = true)AddValueDTO addValueDTO) {
        return new HierarchyDAO(trackItDAO, jdbcTemplate).addValue(userId, addValueDTO);

    }

    @RequestMapping(value = "/trackit/getData/{id}", method = RequestMethod.POST)
    public GetDataResponseDTO getData(@PathVariable("id") String userId, @RequestBody(required = true)GetDataDTO getDataDTO) {
        return  new UserDataDAO(jdbcTemplate).getData(getDataDTO.getPath(), getDataDTO.getName(), userId);
    }

    @RequestMapping(value = "/trackit/getChart/{id}", method = RequestMethod.POST)
    public Map<String,HighChart> getUserCharts(@PathVariable("id") String userId, @RequestBody(required = true)GetDataDTO getDataDTO) {
        return  new UserDataDAO(jdbcTemplate).getType1Charts(userId, getDataDTO);
    }

    @RequestMapping(value = "/trackit/getAggChart/{id}", method = RequestMethod.POST)
    public HighChart getAggChart(@PathVariable("id") String userId, @RequestBody(required = true)AggregationRequestDTO aggregationRequestDTO) {
        AggregationDTO aggregationDTO = aggregationRequestDTO.getAggregationDTO();
        GraphElement graphElement = aggregationRequestDTO.getGraphElement();
        return  new UserDataDAO(jdbcTemplate).getAggChart(userId, new AggregationRequestDTO(aggregationDTO, graphElement));
    }

}
