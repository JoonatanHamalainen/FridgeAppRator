package com.example.fridgeapprator.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "producttype")
public class ProductType {

    @PrimaryKey(autoGenerate = true)
    private int productTypeID;
    @NonNull
    private final String productTypeName;

    private int amount;

    public ProductType(@NonNull String productTypeName, int amount) {
        this.productTypeName = productTypeName;
        this.amount = amount;
    }

    public int getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(int productTypeID) {
        this.productTypeID = productTypeID;
    }

    @NonNull
    public String getProductTypeName() {
        return productTypeName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
