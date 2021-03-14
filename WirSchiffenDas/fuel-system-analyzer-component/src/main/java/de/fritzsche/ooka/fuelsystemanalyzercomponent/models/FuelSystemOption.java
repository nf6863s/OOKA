package de.fritzsche.ooka.oilsystemanalyzerservice.models;

public class OilSystemOption {
    private String serviceId;
    private String optionId;

    public OilSystemOption() {}

    public OilSystemOption(String serviceId, String optionId) {
        this.serviceId = serviceId;
        this.optionId = optionId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    @Override
    public String toString() {
        return "(id: " + serviceId + ", name: " + optionId + ")";
    }
}
