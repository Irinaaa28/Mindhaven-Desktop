package com.irina.mindhaven.mindhavendesktop.session;

import com.irina.mindhaven.mindhavendesktop.rule.RuleDTO;

import java.util.ArrayList;
import java.util.List;

public class SessionContext {
    private static String userUuid;
    private static List<RuleDTO> activeRules = new ArrayList<>();

    public static String getUserUuid() { return userUuid; }
    public static void setUserUuid(String userUuid) { SessionContext.userUuid = userUuid; }
    public static List<RuleDTO> getActiveRules() { return activeRules; }
    public static void setActiveRules(List<RuleDTO> activeRules) { activeRules = activeRules; }
}
