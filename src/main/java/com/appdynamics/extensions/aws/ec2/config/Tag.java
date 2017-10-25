package com.appdynamics.extensions.aws.ec2.config;

import java.util.List;

/**
 * @author Satish Muddam
 */
public class Tag {

    private String name;
    private List<String> value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
}
