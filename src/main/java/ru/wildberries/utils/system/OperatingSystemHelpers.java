package ru.wildberries.utils.system;

import lombok.extern.slf4j.Slf4j;
import ru.wildberries.enums.system.OSType;

import java.util.Locale;

@Slf4j
public final class OperatingSystemHelpers {

    /**
     * detect the operating system from the os.name System property
     *
     * @return - the operating system detected
     */
    public static OSType getOperatingSystemType() {
        OSType detectedSystem;
        String system = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

        if ((system.contains("mac")) || (system.contains("darwin"))) {
            detectedSystem = OSType.MACOS;
        } else if (system.contains("win")) {
            detectedSystem = OSType.WINDOWS;
        } else if (system.contains("nux")) {
            detectedSystem = OSType.LINUX;
        } else {
            detectedSystem = OSType.OTHER;
        }

        log.info("Operating System: {}", detectedSystem);
        return detectedSystem;
    }
}
