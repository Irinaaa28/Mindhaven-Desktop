package com.irina.mindhaven.mindhavendesktop.monitoring;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;

public class ActiveWindowTracker {

    public String getActiveApplication() {
        WinDef.HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        IntByReference pid = new IntByReference();
        User32.INSTANCE.GetWindowThreadProcessId(hwnd, pid);
        long processId = pid.getValue();
        return ProcessHandle.allProcesses()
                .filter(p -> p.pid() == processId)
                .findFirst()
                .flatMap(p -> p.info().command())
                .orElse("Unknown");
    }
}
