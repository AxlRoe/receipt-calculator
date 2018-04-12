package com.sales;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class JsonBean {

    Map<String, String> itemMap;

    @JsonCreator
    public JsonBean(@JsonProperty("items") Map<String, String> itemMap) {
        this.itemMap = itemMap;
    }

    public Map<String, String> getItemMap() {
        return itemMap;
    }
}
