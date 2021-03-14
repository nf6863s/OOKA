package de.fritzsche.ooka.controller.models;

import java.util.List;

public class System {
    private int id;
    private String name;
    private String serviceName;
    private List<Option> options;

    public System() {
    }

    public System(int id, String name, String serviceName, List<Option> options) {
        this.id = id;
        this.name = name;
        this.serviceName = serviceName;
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
