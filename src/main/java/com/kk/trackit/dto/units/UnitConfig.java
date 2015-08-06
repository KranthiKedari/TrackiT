package com.kk.trackit.dto.units;

import org.mongodb.morphia.annotations.Embedded;

import java.util.Map;

/**
 * Created by kkedari on 7/25/15.
 */
@Embedded
public class UnitConfig {
    private Map<String, UnitDefinition> conversions;

    private Map<String, Map<String, UnitDefinition>> mappings;

    private Map<String, String> aliases;

    public Map<String, Map<String, UnitDefinition>> getMappings() {
        return mappings;
    }

    public void setMappings(Map<String, Map<String, UnitDefinition>> mappings) {
        this.mappings = mappings;
    }

    public Map<String, String> getAliases() {
        return aliases;
    }

    public void setAliases(Map<String, String> aliases) {
        this.aliases = aliases;
    }

    public Map<String, UnitDefinition> getConversions() {
        return conversions;
    }

    public void setConversions(Map<String, UnitDefinition> conversions) {
        this.conversions = conversions;
    }
}
