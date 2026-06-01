package com.irina.mindhaven.mindhavendesktop.session;

public class SessionContext {
    private static String userUuid;

    public static String getUserUuid() {
        return userUuid;
    }
    public static void setUserUuid(String userUuid) {
        SessionContext.userUuid = userUuid;
    }
}
