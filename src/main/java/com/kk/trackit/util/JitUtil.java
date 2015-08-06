package com.kk.trackit.util;

import com.kk.trackit.dto.Element;
import com.kk.trackit.dto.Jit.JitElement;
import com.kk.trackit.dto.UserSettings;
import com.kk.trackit.dto.hierarchy.Hierarchy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kkedari on 7/30/15.
 */
public class JitUtil {

    public Map<String,JitElement> getJitHierarchy(UserSettings userSettings) {
        Hierarchy hierarchy = userSettings.getSettings().getHierarchy();
        Map<String, JitElement> response = new HashMap<String, JitElement>();
        for(Map.Entry<String, Element> element : hierarchy.getHierarchy().entrySet()) {
            response.put(element.getKey(), convertElementToJitElement(element.getValue()));
        }

        return response;
    }

    public JitElement convertElementToJitElement(Element element) {
        JitElement jitElement = new JitElement();

        jitElement.setId(element.getName());
        jitElement.setName(element.getName());
        jitElement.setData(new HashMap<String,String>());

        List<JitElement> children = new ArrayList<JitElement>();

        if(element.getChildren() != null) {
            for (Element childElement : element.getChildren().values()) {
                if (childElement != null) {
                    children.add(convertElementToJitElement(childElement));
                }
            }
        }

        jitElement.setChildren(children);
        return jitElement;
    }
}
