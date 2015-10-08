package com.kk.trackit.dto.aggregations;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by kkedari on 8/10/15.
 */
public class AggregationDTO {

    @JsonProperty("fromTime")
    private long fromTime;

    @JsonProperty("endTime")
    private long endTime;

    @JsonProperty("groupBy")
    private String groupBy;



    @JsonProperty("flags")
    private Map<String, Object> flags;

    @JsonProperty("aggregationType")
    private String aggregationType;

    @JsonProperty("interval")
    private Double interval;

    @JsonProperty("intervalType")
    private String intervalType;


    public AggregationDTO() {
        this.interval = 1.0;
        this.intervalType = "month";
        this.fromTime = -1;
        this.endTime = -1;
        this.aggregationType = "sum";

    }

    public Double getInterval() {
        return interval;
    }

    public void setInterval(Double interval) {
        this.interval = interval;
    }

    public String getIntervalType() {
        return intervalType;
    }

    public void setIntervalType(String intervalType) {
        this.intervalType = intervalType;
    }

    public long getFromTime() {
        return fromTime;
    }

    public void setFromTime(long fromTime) {
        this.fromTime = fromTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }


    public Map<String, Object> getFlags() {
        return flags;
    }

    public void setFlags(Map<String, Object> flags) {
        this.flags = flags;
    }

    public String getAggregationType() {
        return aggregationType;
    }

    public void setAggregationType(String aggregationType) {
        this.aggregationType = aggregationType;
    }
}
