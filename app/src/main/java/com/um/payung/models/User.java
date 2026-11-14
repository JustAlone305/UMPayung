package com.um.payung.models;

public class User {
    private String uid;
    private String email;
    private String displayName;
    private String role;
    private String language;
    private String fcmToken;
    private long createdAt;

    public User() {}

    public String getUid() {return uid;}
    public void setUid(String uid) {this.uid = uid;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getDisplayName() {return displayName;}
    public void setDisplayName(String displayName) {this.displayName = displayName;}

    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}

    public String getLanguage() {return language;}
    public void setLanguage(String language) {this.language = language;}

    public String getFcmToken() {return fcmToken;}
    public void setFcmToken(String fcmToken) {this.fcmToken = fcmToken;}

    public long getCreatedAt() {return createdAt;}
    public void setCreatedAt(long createdAt) {this.createdAt = createdAt;}
}
