package com.kk.trackit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kk.trackit.dto.hierarchy.Assignments;
import com.kk.trackit.dto.hierarchy.Hierarchy;
import com.kk.trackit.dto.units.UnitConfig;
import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by kkedari on 7/25/15.
 */
@Embedded
public class Settings {
    @JsonProperty("hierarchy")
    private Hierarchy hierarchy;

    @JsonProperty("assignments")
    private Assignments assignments;

    @JsonProperty("unitConfig")
    private UnitConfig unitConfig;

    public Settings() {

    }
    public Settings(Hierarchy hierarchy, UnitConfig unitConfig, Assignments assignments) {
        this.hierarchy = hierarchy;
        this.unitConfig = unitConfig;
        this.assignments = assignments;
    }

    public Hierarchy getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(Hierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Assignments getAssignments() {
        return assignments;
    }

    public void setAssignments(Assignments assignments) {
        this.assignments = assignments;
    }

    public UnitConfig getUnitConfig() {
        return unitConfig;
    }

    public void setUnitConfig(UnitConfig unitConfig) {
        this.unitConfig = unitConfig;
    }
}
