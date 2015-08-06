package com.kk.trackit.dto.hierarchy;

import com.kk.trackit.dto.Element;
import org.apache.commons.lang3.ArrayUtils;
import org.mongodb.morphia.annotations.Embedded;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kkedari on 7/13/15.
 */
@Embedded
public class Hierarchy {

    private Map<String, Element> hierarchy;

    public Map<String, Element> getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(Map<String, Element> hierarchy) {
        this.hierarchy = hierarchy;
    }


    public Hierarchy addChildNode(String path, Element element) {
        String[] route = path.split("\\|");
        Element trace = null;
        for(String routeField : route) {
            if(trace == null) {
                Element routeElement = hierarchy.get(routeField);
                trace = routeElement;
            } else if(trace.getChildren().containsKey(routeField)) {
                trace = trace.getChildren().get(routeField);

            } else  {
                return null;
            }
        }
        if(trace.getChildren() == null) {
            trace.setChildren(new HashMap<String, Element>());
        }
        trace.getChildren().put(element.getName(), element);

        return this;
    }


    public Hierarchy addGroup(Element group) {
        hierarchy.put(group.getName(), group);
        return this;
    }
}
