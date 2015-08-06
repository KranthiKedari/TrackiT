package com.kk.trackit.dto.Jit;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by kkedari on 7/30/15.
 */
public class JitElement {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("data")
    private Map<String, String> data;

    @JsonProperty("children")
    private List<JitElement> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public List<JitElement> getChildren() {
        return children;
    }

    public void setChildren(List<JitElement> children) {
        this.children = children;
    }
}
