package com.irina.mindhaven.mindhavendesktop.monitoring;

import com.irina.mindhaven.mindhavendesktop.activity.ActivityEvent;
import com.irina.mindhaven.mindhavendesktop.activity.ActivitySenderService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ActivityScheduler {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ActivityMonitorService monitor = new ActivityMonitorService();
    private final ActivitySenderService sender = new ActivitySenderService();

    public void start() {
        scheduler.scheduleAtFixedRate(() -> {
            ActivityEvent event = monitor.collectActivity();
            sender.send(event);
        }, 0, 5, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduler.shutdown();
    }
}