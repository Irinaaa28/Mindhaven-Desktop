package com.irina.mindhaven.mindhavendesktop.auth;

public class AccountLockedException extends RuntimeException {

    private final long ttlSeconds;

    public AccountLockedException(long ttlSeconds) {
        super("Account locked");
        this.ttlSeconds = ttlSeconds;
    }

    public long getTtlSeconds() {
        return ttlSeconds;
    }
}
