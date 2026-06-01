package com.irina.mindhaven.mindhavendesktop.monitoring;

import com.irina.mindhaven.mindhavendesktop.activity.ActivityEvent;
import com.irina.mindhaven.mindhavendesktop.session.SessionContext;

import java.time.Duration;
import java.time.LocalDateTime;

public class UsageTracker {

    private String currentApplication;
    private LocalDateTime startTime;

    public ActivityEvent track(String newApplication) {
        if (currentApplication == null) {
            currentApplication = newApplication;
            startTime = LocalDateTime.now();
            return null;
        }
        if (currentApplication.equals(newApplication))
            return null;
        LocalDateTime endTime = LocalDateTime.now();
        long duration = Duration.between(startTime, endTime).toSeconds();
        ActivityEvent event = new ActivityEvent(SessionContext.getUserUuid(), currentApplication, startTime, endTime, duration);
        currentApplication = newApplication;
        startTime = LocalDateTime.now();
        return event;
    }
}
