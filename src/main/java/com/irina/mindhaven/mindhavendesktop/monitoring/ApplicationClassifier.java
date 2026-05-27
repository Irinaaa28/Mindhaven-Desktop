package com.irina.mindhaven.mindhavendesktop.monitoring;

public class ApplicationClassifier {
    public String classify(String processName) {
        processName = processName.toLowerCase();

        if (processName.contains("chrome") || processName.contains("firefox") || processName.contains("msedge"))
            return "BROWSER";
        if (processName.contains("discord") || processName.contains("teams") || processName.contains("skype"))
            return "SOCIAL";
        if (processName.contains("steam"))
            return "GAMING";
        if (processName.contains("instagram"))
            return "MEDIA";
        return "OTHER";
    }
}
