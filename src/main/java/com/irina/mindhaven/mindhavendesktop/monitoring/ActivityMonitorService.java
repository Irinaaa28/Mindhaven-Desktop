package com.irina.mindhaven.mindhavendesktop.monitoring;

import com.irina.mindhaven.mindhavendesktop.activity.ActivityEvent;
import com.irina.mindhaven.mindhavendesktop.process.ProcessInfo;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

public class ActivityMonitorService {

    private final ActiveWindowTracker tracker = new ActiveWindowTracker();

    public ActivityEvent collectActivity() {
//        List<ProcessInfo> processes = processScanner.getProcesses();
//        processes = processFilter.filterUserProcesses(processes);
//        processes.forEach(p -> System.out.println(p.getName()));
//        return new ActivityEvent(processes.isEmpty() ? "Unknown" : processes.get(0).getName(), LocalDateTime.now(), 5);

        String activeApp = tracker.getActiveApplication();
        String filename = Paths.get(activeApp).getFileName().toString();
        System.out.println("Active app: " + filename);
        return new ActivityEvent(activeApp, LocalDateTime.now(), 5);
    }
}