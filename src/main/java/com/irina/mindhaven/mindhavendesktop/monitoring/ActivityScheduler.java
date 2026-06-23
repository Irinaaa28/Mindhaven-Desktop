package com.irina.mindhaven.mindhavendesktop.monitoring;

import com.irina.mindhaven.mindhavendesktop.activity.ActivityEvent;
import com.irina.mindhaven.mindhavendesktop.activity.ActivitySenderService;
import com.irina.mindhaven.mindhavendesktop.session.SessionContext;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ActivityScheduler {
    //create a separate thread for monitoring
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ActivityMonitorService monitor = new ActivityMonitorService();
    private final ActivitySenderService sender = new ActivitySenderService();

    private String lastBlockedApp = "";
    private long lastBlockedTime = 0;

    public void start() {
        // execute the code periodically
        scheduler.scheduleAtFixedRate(() -> {
            ActivityEvent event = monitor.collectActivity();
            long now = System.currentTimeMillis();
            if (event != null) {
                if (event.getApplicationName().equals(lastBlockedApp) && (now - lastBlockedTime < 2500))
                    return;
                sender.send(event);
            } else {
                String activeApp = monitor.getTracker().getActiveApplication();
                String filename = Paths.get(activeApp).getFileName().toString();
                if (!filename.equalsIgnoreCase("Unknown") && !filename.isEmpty()) {
                    ActivityEvent verificationEvent = new ActivityEvent(SessionContext.getUserUuid(), filename,
                            LocalDateTime.now().minusSeconds(2), LocalDateTime.now(), 2);
                    lastBlockedApp = filename;
                    lastBlockedTime = System.currentTimeMillis();
                    sender.send(verificationEvent);
                }
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduler.shutdown();
    }
}