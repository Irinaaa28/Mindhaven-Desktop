package com.irina.mindhaven.mindhavendesktop.monitoring;

import com.irina.mindhaven.mindhavendesktop.activity.ActivityEvent;

import java.time.LocalDateTime;

public class ActivityMonitorService {

    public ActivityEvent collectActivity() {
        return new ActivityEvent("Unknown", LocalDateTime.now(), 0);
    }
}