package com.kk.trackit.dto.aggregations;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by kkedari on 8/4/15.
 */
public class SeriesDTO {
    @JsonProperty("name")
    private String name;

    @JsonProperty("data")
    private List<List<Object>> data;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
    }
}
