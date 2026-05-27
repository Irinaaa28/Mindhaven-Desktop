package com.irina.mindhaven.mindhavendesktop.process;

public class ProcessInfo {
    private long pid;
    private String name;
    private String fullPath;
    private boolean systemProcess;

    public ProcessInfo(long pid, String name, String fullPath, boolean systemProcess) {
        this.pid = pid;
        this.name = name;
        this.fullPath = fullPath;
        this.systemProcess = systemProcess;
    }

    public long getPid() {
        return pid;
    }
    public String getName() {
        return name;
    }
    public String getFullPath() {
        return fullPath;
    }
    public boolean isSystemProcess() {
        return systemProcess;
    }
}
