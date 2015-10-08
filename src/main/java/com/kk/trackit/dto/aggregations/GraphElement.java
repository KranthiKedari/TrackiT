package com.kk.trackit.dto.aggregations;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by kkedari on 8/10/15.
 */
public class GraphElement {
    @JsonProperty("path")
    private String path;

    @JsonProperty("name")
    private String name;

    @JsonProperty("values")
    private Map<String, String> values;

    @JsonProperty("graphType")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
