package com.kk.trackit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by kkedari on 8/4/15.
 */
public class GetDataResponseDTO {
    @JsonProperty("path")
    private String title;

    @JsonProperty("unit")
    private String unit;
    @JsonProperty("data")
    private Map<String, List<SeriesDTO>> data;

    @JsonProperty("fields")
    private Map<String, Object> fields;

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Map<String, List<SeriesDTO>> getData() {
        return data;
    }

    public void setData(Map<String, List<SeriesDTO>> data) {
        this.data = data;
    }
}
