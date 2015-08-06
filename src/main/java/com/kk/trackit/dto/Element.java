package com.kk.trackit.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by kkedari on 7/13/15.
 */
public class Element {
    private String id;

    private String name;

    private Map<String, String> data;

    private Map<String, Element> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Element> getChildren() {
        return children;
    }

    public void setChildren(Map<String, Element> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
