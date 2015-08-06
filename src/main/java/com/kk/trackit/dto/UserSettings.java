package com.kk.trackit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

/**
 * Created by kkedari on 7/13/15.
 */
@Entity
public class UserSettings {
    @Id
    @JsonProperty("id")
    private ObjectId id;

    @Property("userId")
    @JsonProperty("userId")
    private String userId;

    @Embedded
    @JsonProperty("settings")
    private Settings settings;

    public UserSettings() {

    }
    public UserSettings(String userId, Settings settings) {
        this.settings = settings;

        this.userId = userId;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
