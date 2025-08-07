package com.practica.mdm2.mdm2.etl.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductoProveedorB {

    @JsonProperty("productCode")
    private String productCode;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("categoryRef")
    private String categoryRef;

    @JsonProperty("priceData")
    private PriceData priceData;


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryRef() {
        return categoryRef;
    }

    public void setCategoryRef(String categoryRef) {
        this.categoryRef = categoryRef;
    }

    public PriceData getPriceData() {
        return priceData;
    }

    public void setPriceData(PriceData priceData) {
        this.priceData = priceData;
    }
}