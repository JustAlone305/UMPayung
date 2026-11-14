package com.um.payung.models;

public class WeatherAlert {
    private String id;
    private String type;        // RAIN / STORM / HAZE
    private String title;
    private String message;
    private String severity;    // LOW / MEDIUM / HIGH
    private int apiValue;
    private String source;      // MET / APIMS
    private long issuedAt;
    private long expiresAt;

    public WeatherAlert() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public int getApiValue() { return apiValue; }
    public void setApiValue(int apiValue) { this.apiValue = apiValue; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public long getIssuedAt() { return issuedAt; }
    public void setIssuedAt(long issuedAt) { this.issuedAt = issuedAt; }

    public long getExpiresAt() { return expiresAt; }
    public void setExpiresAt(long expiresAt) { this.expiresAt = expiresAt; }
}
