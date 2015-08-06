package com.kk.trackit.dto.units;

import java.util.Map;

/**
 * Created by kkedari on 7/25/15.
 */
public class UnitConversions {

    private Map<String, UnitDefinition> conversions;

    public Map<String, UnitDefinition> getConversions() {
        return conversions;
    }

    public void setConversions(Map<String, UnitDefinition> conversions) {
        this.conversions = conversions;
    }
}
