package com.kk.trackit.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kk.trackit.dto.Element;

/**
 * Created by kkedari on 8/3/15.
 */
public class AddNodeDTO {
    @JsonProperty("path")
    private String path;
    @JsonProperty("node")
    private Element node;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Element getNode() {
        return node;
    }

    public void setNode(Element node) {
        this.node = node;
    }
}
