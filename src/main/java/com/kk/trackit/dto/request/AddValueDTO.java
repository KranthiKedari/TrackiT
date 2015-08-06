package com.kk.trackit.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kk.trackit.dto.Element;

/**
 * Created by kkedari on 8/4/15.
 */
public class AddValueDTO {
    @JsonProperty("path")
    private String path;

    @JsonProperty("name")
    private String name;

    @JsonProperty("value")
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
