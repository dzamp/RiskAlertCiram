package com.services.AresiboClasses;

class Coords {
    private double longitude;
    private double latitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

class Location {
    private Coords coordinates;
    private String type = "Point";
    private float radius = 0;

    public Coords getCoordinates() {
        return coordinates;
    }

    public float getRadius() {
        return radius;
    }

    public String getType() {
        return type;
    }

    public void setCoordinates(double lon, double lat) {
        this.coordinates = new Coords();
        this.coordinates.setLatitude(lat);
        this.coordinates.setLongitude(lon);
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setType(String type) {
        this.type = type;
    }
}

class CIRAM_dets {
    private String impact;
    private String threat;
    private String vulnerability;

    public String getVulnerability() {
        return vulnerability;
    }

    public String getImpact() {
        return impact;
    }

    public String getThreat() {
        return threat;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public void setThreat(String threat) {
        this.threat = threat;
    }

    public void setVulnerability(String vulnerability) {
        this.vulnerability = vulnerability;
    }
}

public class Alert {
    private int alert_id;
    private String alert_level;
    private String alert_status = "ALERT_ACTIVE";
    private String alert_text = "Unidentified vessel is approaching restricted area";
    private String alert_title = "Warning";
    private long timestamp;
    private long alert_end_time;
    private long alert_start_time;
    private int tracked_entity_id;
    private Location location;
    private CIRAM_dets ciram_details;

    public void setAlert_end_time(long alert_end_time) {
        this.alert_end_time = alert_end_time;
    }

    public void setAlert_id(int alert_id) {
        this.alert_id = alert_id;
    }

    public void setAlert_level(String alert_level) {
        this.alert_level = alert_level;
    }

    public void setAlert_start_time(long alert_start_time) {
        this.alert_start_time = alert_start_time;
    }

    public void setAlert_status(String alert_status) {
        this.alert_status = alert_status;
    }

    public void setAlert_text(String alert_text) {
        this.alert_text = alert_text;
    }

    public void setAlert_title(String alert_title) {
        this.alert_title = alert_title;
    }

    public void setCiram_details(Levels riskLevels) {
        this.ciram_details = new CIRAM_dets();
        this.ciram_details.setImpact(riskLevels.getImpact().toUpperCase());
        this.ciram_details.setThreat(riskLevels.getThreat().toUpperCase());
        this.ciram_details.setVulnerability(riskLevels.getVulnerability().toUpperCase());
    }

    public void setLocation(double lon, double lat) {
        this.location = new Location();
        this.location.setCoordinates(lon, lat);
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setTracked_entity_id(int tracked_entity_id) {
        this.tracked_entity_id = tracked_entity_id;
    }

    public CIRAM_dets getCiram_details() {
        return ciram_details;
    }

    public int getAlert_id() {
        return alert_id;
    }

    public int getTracked_entity_id() {
        return tracked_entity_id;
    }

    public String getAlert_level() {
        return alert_level;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Location getLocation() {
        return location;
    }

    public String getAlert_status() {
        return alert_status;
    }

    public long getAlert_end_time() {
        return alert_end_time;
    }

    public long getAlert_start_time() {
        return alert_start_time;
    }

    public String getAlert_text() {
        return alert_text;
    }

    public String getAlert_title() {
        return alert_title;
    }
}