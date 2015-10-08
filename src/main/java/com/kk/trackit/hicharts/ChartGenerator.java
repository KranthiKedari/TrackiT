package com.kk.trackit.hicharts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kk.trackit.dto.aggregations.SeriesDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by kkedari on 8/6/15.
 */
public class ChartGenerator {

    private PlotOptions plotOptions;

    public ChartGenerator() {
        this.plotOptions = getPlotOptions();
    }
    public PlotOptions getPlotOptions() {
        InputStream plotOptionsStream =
                getClass().getResourceAsStream("/json/plotOptions.json");

        ObjectMapper mapper = new ObjectMapper();
        PlotOptions plotOptions = null;
        try {
            plotOptions = mapper.readValue(plotOptionsStream, PlotOptions.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return plotOptions;
    }

    public HighChart generateHighChart(String type, Map<String, SeriesDTO> seriesDTOMap) {


        return null;
    }


    public Object getPlotOptions(String type) {
        return this.plotOptions.getPlotOptions().get(type);
    }
}
