package com.kk.trackit.hicharts;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kkedari on 8/6/15.
 */
public class Axis {
    @JsonProperty("title")
    private AxisTitle title;

    @JsonProperty("type")
    private String type;

    @JsonProperty("labels")
    private AxisLabel labels;


    public Axis() {
        this.title = new AxisTitle();
        this.type = null;
        this.labels = new AxisLabel();
    }

    public AxisTitle getTitle() {
        return title;
    }

    public void setTitle(AxisTitle title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AxisLabel getLabels() {
        return labels;
    }

    public void setLabels(AxisLabel labels) {
        this.labels = labels;
    }


}
