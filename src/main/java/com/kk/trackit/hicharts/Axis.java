package com.kk.trackit.hicharts;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kkedari on 8/6/15.
 */
public class Axis {
    @JsonProperty("title")
    private Title title;

    @JsonProperty("type")
    private String type;

    @JsonProperty("labels")
    private AxisLabel labels;

    @JsonProperty("showFirstLabel")
    private boolean showFirstLabel;

    public Axis() {
        this.title = new Title();
        this.type = "";
        this.showFirstLabel = true;
    }

}
