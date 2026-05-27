package com.irina.mindhaven.mindhavendesktop.activity;

public class ActivitySenderService {

    public void send(ActivityEvent event) {
        if (event == null)
            return;
        System.out.println("\nApplication switched:");
        System.out.println(event.getApplicationName());
        System.out.println("Duration: " + event.getDurationSeconds() + " sec");
    }
}
