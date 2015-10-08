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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZoomType() {
        return zoomType;
    }

    public void setZoomType(String zoomType) {
        this.zoomType = zoomType;
    }

    public Options3D getOptions3d() {
        return options3d;
    }

    public void setOptions3d(Options3D options3d) {
        this.options3d = options3d;
    }
}
