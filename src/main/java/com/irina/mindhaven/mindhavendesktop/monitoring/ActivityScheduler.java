package com.irina.mindhaven.mindhavendesktop.monitoring;

import com.irina.mindhaven.mindhavendesktop.activity.ActivityEvent;
import com.irina.mindhaven.mindhavendesktop.activity.ActivitySenderService;
import com.irina.mindhaven.mindhavendesktop.session.SessionContext;

import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
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
    private Instant lastBlockedTime = Instant.MIN;

    public void start() {
        // execute the code periodically
        scheduler.scheduleAtFixedRate(() -> {
            ActivityEvent event = monitor.collectActivity();
            if (event != null)
                sender.send(event);
            else {
                String activeApp = monitor.getTracker().getActiveApplication();
                String filename = Paths.get(activeApp).getFileName().toString();
                if (!filename.equalsIgnoreCase("Unknown") && !filename.isEmpty()) {
                    if (filename.equals(lastBlockedApp) && Duration.between(lastBlockedTime, Instant.now()).getSeconds() < 3) {
                        try {
                            Runtime.getRuntime().exec("taskkill /F /IM " + filename);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    ActivityEvent verificationEvent = new ActivityEvent(SessionContext.getUserUuid(), filename, LocalDateTime.now(), LocalDateTime.now(), 0);
                    boolean wasBlocked = sender.send(verificationEvent);
                    if (wasBlocked) {
                        lastBlockedApp = filename;
                        lastBlockedTime = Instant.now();
                    }
                }
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduler.shutdown();
    }
}