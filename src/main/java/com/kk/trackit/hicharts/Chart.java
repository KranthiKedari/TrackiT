package com.kk.trackit.hicharts;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kkedari on 8/6/15.
 */
public class Chart {

    @JsonProperty("type")
    private String type;

    @JsonProperty("zoomType")
    private String zoomType;

    private Options3D options3d;

    public Chart() {
        this.type = "line";
        this.zoomType = "x";
    }
}
