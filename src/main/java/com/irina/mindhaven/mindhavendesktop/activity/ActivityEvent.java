package com.irina.mindhaven.mindhavendesktop.activity;

import java.time.LocalDateTime;

public class ActivityEvent {

    private String applicationName;
    private LocalDateTime timestamp;
    private int durationSeconds;

    public ActivityEvent() {}

    public ActivityEvent(String applicationName, LocalDateTime timestamp, int durationSeconds) {
        this.applicationName = applicationName;
        this.timestamp = timestamp;
        this.durationSeconds = durationSeconds;
    }

    public String getApplicationName() {
        return applicationName;
    }
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public int getDurationSeconds() {
        return durationSeconds;
    }
    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }
}