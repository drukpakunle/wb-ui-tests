package ru.wildberries.models.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Device {

    @JsonProperty
    public String name;

    @JsonProperty
    public int width;

    @JsonProperty
    public int height;

    @JsonProperty
    public double pixelRatio;

    @JsonProperty
    public String userAgent;

}
