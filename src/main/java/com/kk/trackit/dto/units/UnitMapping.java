package com.kk.trackit.dto.units;

import java.util.Map;

/**
 * Created by kkedari on 7/20/15.
 */
public class UnitMapping {

    public UnitMapping(Map<String, Map<String, UnitConfig>> mappings) {
        this.mappings = mappings;
    }
    private Map<String, Map<String, UnitConfig>> mappings;

}
