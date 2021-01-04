package com.services.AresiboClasses;

public class Levels {
    private String name;
    private String risk;
    private String impact;
    private String vulnerability;
    private String threat;

    public String getName() {
        return name;
    }

    public String getImpact() {
        return impact;
    }

    public String getRisk() {
        return risk;
    }

    public String getThreat() {
        return threat;
    }

    public String getVulnerability() {
        return vulnerability;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public void setThreat(String threat) {
        this.threat = threat;
    }

    public void setVulnerability(String vulnerability) {
        this.vulnerability = vulnerability;
    }
}
