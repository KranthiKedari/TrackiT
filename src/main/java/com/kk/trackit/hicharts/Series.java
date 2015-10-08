package com.kk.trackit.hicharts;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kkedari on 8/6/15.
 */
public class Series {
    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty("data")
    private List<List<Object>> data;

    public String getName() {
        return name;
    }

    public Series()
    {
        this.data = new ArrayList<List<Object>>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
    }
}
