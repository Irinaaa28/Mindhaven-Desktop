package com.irina.mindhaven.mindhavendesktop.activity;

public class ActivitySenderService {

    public void send(ActivityEvent activityEvent) {
        System.out.println("Sending activity: " + activityEvent.getApplicationName());
    }
}
