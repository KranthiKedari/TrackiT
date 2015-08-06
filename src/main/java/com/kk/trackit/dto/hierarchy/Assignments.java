package com.kk.trackit.dto.hierarchy;

import org.mongodb.morphia.annotations.Embedded;

import java.util.List;
import java.util.Map;

/**
 * Created by kkedari on 7/20/15.
 */
@Embedded
public class Assignments {
    private Map<String, List<String>> assignments;

    public Map<String, List<String>> getAssignments() {
        return assignments;
    }

    public void setAssignments(Map<String, List<String>> assignments) {
        this.assignments = assignments;
    }

    private String getAlias(String key) {
        if(key.equals("$")) {
            return "dollar";
        }
        return key;
    }
}
