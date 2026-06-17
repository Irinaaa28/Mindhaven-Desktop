package com.irina.mindhaven.mindhavendesktop.rule;

public class RuleDecision {

    private boolean blocked;
    private String reason;

    public RuleDecision() {}

    public RuleDecision(boolean blocked, String reason) {
        this.blocked = blocked;
        this.reason = reason;
    }

    public boolean isBlocked() { return blocked; }
    public void setBlocked(boolean blocked) { this.blocked = blocked; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
