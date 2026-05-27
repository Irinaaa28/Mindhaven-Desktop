package com.irina.mindhaven.mindhavendesktop.monitoring;

import com.irina.mindhaven.mindhavendesktop.process.ProcessInfo;

import java.util.List;

public class ProcessFilter {
//    public List<ProcessInfo> filterUserProcesses(List<ProcessInfo> processes) {
//        return processes.stream().filter(p -> !p.isSystemProcess()).toList();
//    }

    public boolean shouldIgnore(ProcessInfo process) {
        return process.isSystemProcess();
    }
}
