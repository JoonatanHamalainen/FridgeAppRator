package com.example.fridgeapprator.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shoppinglistproduct")
public class ShoppingListProduct {
    @PrimaryKey(autoGenerate = true)
    private int shoppingListProductID;
    @NonNull
    private String productTypeName;

    private int amount, shoppingListID;

    public ShoppingListProduct(@NonNull String productTypeName, int amount, int shoppingListID) {
        this.productTypeName = productTypeName;
        this.amount = amount;
        this.shoppingListID = shoppingListID;
    }

    public int getShoppingListProductID() {
        return shoppingListProductID;
    }

    public void setShoppingListProductID(int shoppingListProductID) {
        this.shoppingListProductID = shoppingListProductID;
    }

    @NonNull
    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(@NonNull String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getShoppingListID() {
        return shoppingListID;
    }

    public void setShoppingListID(int shoppingListID) {
        this.shoppingListID = shoppingListID;
    }
}
