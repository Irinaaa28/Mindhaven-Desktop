package com.irina.mindhaven.mindhavendesktop.ratelimit;

public class RateLimitExceededException extends RuntimeException {

    public RateLimitExceededException() {
        super("Rate limit exceeded");
    }
}
