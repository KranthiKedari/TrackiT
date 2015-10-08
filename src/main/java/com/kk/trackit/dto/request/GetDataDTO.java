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

    @JsonProperty("fromTime")
    private long fromTime;

    @JsonProperty("toTime")
    private long toTime;

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

    public long getFromTime() {
        return fromTime;
    }

    public void setFromTime(long fromTime) {
        this.fromTime = fromTime;
    }

    public long getToTime() {
        return toTime;
    }

    public void setToTime(long toTime) {
        this.toTime = toTime;
    }
}
