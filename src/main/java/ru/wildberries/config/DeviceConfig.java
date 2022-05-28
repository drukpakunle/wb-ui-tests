package ru.wildberries.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.wildberries.dataprovider.DtoController;
import ru.wildberries.models.config.Device;

import java.util.List;

public final class DeviceConfig {

    @JsonProperty("devices")
    public List<Device> devices;

    public static Device getDevice(String deviceName) {
        return new DtoController<>(DeviceConfig.class)
                .getDataFromFile("devices/devices.json")
                .devices.stream()
                .filter(device -> device.name.equalsIgnoreCase(deviceName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No Device with name: " + deviceName));
    }

}
