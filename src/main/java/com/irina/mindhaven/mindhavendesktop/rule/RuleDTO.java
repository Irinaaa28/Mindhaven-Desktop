package com.irina.mindhaven.mindhavendesktop.rule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.irina.mindhaven.mindhavendesktop.user.UserDTO;

import java.time.LocalDateTime;
import java.time.LocalTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RuleDTO {
    private Long id;
    private String userUuid;
    private String type;
    private String category;
    private String value;
    private Integer maxMinutes;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDateTime expiresAt;
    private boolean active;

    public RuleDTO() {}
    public RuleDTO(Long id, String userUuid, String type, String category, String value, Integer maxMinutes, LocalTime startTime, LocalTime endTime, LocalDateTime expiresAt, boolean active) {
        this.id = id;
        this.userUuid = userUuid;
        this.type = type;
        this.category = category;
        this.value = value;
        this.maxMinutes = maxMinutes;
        this.startTime = startTime;
        this.endTime = endTime;
        this.expiresAt = expiresAt;
        this.active = active;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserUuid() { return userUuid; }
    public void setUserUuid(String userUuid) { this.userUuid = userUuid; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public Integer getMaxMinutes() { return maxMinutes; }
    public void setMaxMinutes(Integer maxMinutes) { this.maxMinutes = maxMinutes; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
