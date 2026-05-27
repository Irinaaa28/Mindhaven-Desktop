package com.irina.mindhaven.mindhavendesktop.monitoring;

import com.irina.mindhaven.mindhavendesktop.process.ProcessInfo;

import java.nio.file.Paths;
import java.util.List;

public class ProcessScanner {

    public void printRunningProcesses() {
        ProcessHandle.allProcesses()
                .forEach(process -> {
                    String command = process.info()
                                    .command()
                                    .orElse("Unknown");
                    long pid = process.pid();
                    System.out.println("PID: " + pid + " | " + command);
                });
    }

    public List<ProcessInfo> getProcesses() {
        return ProcessHandle.allProcesses()
                .map(this::convert)
                .filter(p -> p != null)
                .toList();
    }

    private ProcessInfo convert(ProcessHandle process) {
        try {
            String path = process.info().command().orElse("");
            if (path.isBlank())
                return null;
            String filename = Paths.get(path).getFileName().toString();
            boolean system = isSystemProcess(path);
            return new ProcessInfo(process.pid(), filename, path, system);
        } catch(Exception e) {
            return null;
        }
    }

    private boolean isSystemProcess(String path) {
        path = path.toLowerCase();
        return path.contains("system32") || path.contains("system64");
    }
}
