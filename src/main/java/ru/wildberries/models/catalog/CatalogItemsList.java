package ru.wildberries.models.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CatalogItemsList {

    public boolean exist;

    public boolean available;

    @JsonProperty("catalogList")
    public List<CatalogItem> catalogList;

}
