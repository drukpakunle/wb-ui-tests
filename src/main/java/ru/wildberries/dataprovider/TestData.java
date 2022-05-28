package ru.wildberries.dataprovider;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.wildberries.models.basket.Delivery;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.models.catalog.CatalogItemsList;
import ru.wildberries.models.catalog.Category;
import ru.wildberries.models.catalog.Vendor;
import ru.wildberries.models.redirects.RedirectRules;
import ru.wildberries.models.search.SearchAssociations;
import ru.wildberries.models.user.User;

import java.util.List;

public class TestData {

    @JsonProperty("users")
    public List<User> userList;

    @JsonProperty("catalogItems")
    public List<CatalogItem> catalogItemList;

    @JsonProperty("catalogItemsList")
    public List<CatalogItemsList> listOfCatalogItemsList;

    @JsonProperty("vendors")
    public List<Vendor> vendorList;

    @JsonProperty("categories")
    public List<Category> categoryList;

    @JsonProperty("searchAssociations")
    public List<SearchAssociations> searchAssociationsList;

    @JsonProperty("deliveryItems")
    public List<Delivery> deliveryList;

    @JsonProperty("rules")
    public RedirectRules redirectRules;

}
