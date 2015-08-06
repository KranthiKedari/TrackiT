package com.kk.trackit.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by kkedari on 8/4/15.
 */
public class GetDataDTO {

    @JsonProperty("path")
    private String path;

    @JsonProperty("name")
    private String name;

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
