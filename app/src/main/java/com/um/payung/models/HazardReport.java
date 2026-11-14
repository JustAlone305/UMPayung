package com.um.payung.models;

public class HazardReport {
    private String id;
    private String type;        // Flood / Fallen_tree / Blocked_drain / Haze
    private String severity;    // Low / Medium / High
    private String status;      // Reported / Verified / Cleared
    private String description;
    private double latitude;
    private double longitude;
    private long timestamp;
    private String photoUrl;
    private int reportCount;
    private String reporterId;
    private String edgeId;
    private long updatedAt;

    public HazardReport() {}

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public String getSeverity() {return severity;}
    public void setSeverity(String severity) {this.severity = severity;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public double getLatitude() {return latitude;}
    public void setLatitude(double latitude) {this.latitude = latitude;}

    public double getLongitude() {return longitude;}
    public void setLongitude(double longitude) {this.longitude = longitude;}

    public long getTimestamp() {return timestamp;}
    public void setTimestamp(long timestamp) {this.timestamp = timestamp;}

    public String getPhotoUrl() {return photoUrl;}
    public void setPhotoUrl(String photoUrl) {this.photoUrl = photoUrl;}

    public int getReportCount() {return reportCount;}
    public void setReportCount(int reportCount) {this.reportCount = reportCount;}

    public String getReporterId() {return reporterId;}
    public void setReporterId(String reporterId) {this.reporterId = reporterId;}

    public String getEdgeId() {return edgeId;}
    public void setEdgeId(String edgeId) {this.edgeId = edgeId;}

    public long getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(long updatedAt) {this.updatedAt = updatedAt;}
}
