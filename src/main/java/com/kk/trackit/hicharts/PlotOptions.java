package com.kk.trackit.hicharts;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by kkedari on 8/6/15.
 */
public class PlotOptions {
    @JsonProperty
    private Map<String, Object> plotOptions;

    public Map<String, Object> getPlotOptions() {
        return plotOptions;
    }

    public void setPlotOptions(Map<String, Object> plotOptions) {
        this.plotOptions = plotOptions;
    }
}
