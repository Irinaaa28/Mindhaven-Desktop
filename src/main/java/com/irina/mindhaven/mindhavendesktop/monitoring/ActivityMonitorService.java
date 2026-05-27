package com.irina.mindhaven.mindhavendesktop.monitoring;

import com.irina.mindhaven.mindhavendesktop.activity.ActivityEvent;
import java.nio.file.Paths;

public class ActivityMonitorService {

    private final ActiveWindowTracker tracker = new ActiveWindowTracker();
    private final UsageTracker usageTracker = new UsageTracker();

    public ActivityEvent collectActivity() {
        String activeApp = tracker.getActiveApplication();
        String filename = Paths.get(activeApp).getFileName().toString();
        System.out.println("Active app: " + filename);
        return usageTracker.track(filename);
    }
}