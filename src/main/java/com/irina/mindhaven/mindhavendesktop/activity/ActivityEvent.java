package com.irina.mindhaven.mindhavendesktop.activity;

import java.time.LocalDateTime;

public class ActivityEvent {

    private String applicationName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long durationSeconds;

    public ActivityEvent() {}

    public ActivityEvent(String applicationName, LocalDateTime startTime,LocalDateTime endTime, long durationSeconds) {
        this.applicationName = applicationName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationSeconds = durationSeconds;
    }

    public String getApplicationName() {
        return applicationName;
    }
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public long getDurationSeconds() {
        return durationSeconds;
    }
    public void setDurationSeconds(long durationSeconds) {
        this.durationSeconds = durationSeconds;
    }
}