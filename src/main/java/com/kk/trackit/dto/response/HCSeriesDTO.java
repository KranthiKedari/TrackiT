package com.kk.trackit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kk.trackit.dto.aggregations.SeriesDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kkedari on 8/5/15.
 */
public class HCSeriesDTO extends SeriesDTO {

    @JsonProperty("type")
    private String type;

    @JsonProperty("cursor")
    private String cursor;

    @JsonProperty("legend")
    private Map<String, Object> legend;

    @JsonProperty("tooltip")
    private Map<String, Object> tooltip;

    public HCSeriesDTO() {
        this.type = "line";
        this.cursor = "pointer";


        this.legend = new HashMap<String, Object>();
        legend.put("align", "left");
        legend.put("verticalAlign", "top");
        legend.put("y", 20);
        legend.put("floating", true);
        legend.put("borderWidth", 0);

        this.tooltip = new HashMap<String, Object>();
        tooltip.put("shared", true);
        tooltip.put("crosshairs", true);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public Map<String, Object> getLegend() {
        return legend;
    }

    public void setLegend(Map<String, Object> legend) {
        this.legend = legend;
    }

    public Map<String, Object> getTooltip() {
        return tooltip;
    }

    public void setTooltip(Map<String, Object> tooltip) {
        this.tooltip = tooltip;
    }
}
