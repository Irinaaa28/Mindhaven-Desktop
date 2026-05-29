package com.irina.mindhaven.mindhavendesktop.activity;

import com.irina.mindhaven.mindhavendesktop.api.ApiClient;

public class ActivitySenderService {

    private final ApiClient apiClient = new ApiClient();

    public void send(ActivityEvent event) {
        if (event == null)
            return;
        try {
            System.out.println("\nApplication switched:");
            System.out.println(event.getApplicationName());
            System.out.println("Duration: " + event.getDurationSeconds() + " sec");
            apiClient.sendActivity(event);
            System.out.println("Activity sent");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
