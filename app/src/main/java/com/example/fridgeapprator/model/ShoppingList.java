package com.example.fridgeapprator.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shoppinglist")
public class ShoppingList {
    @PrimaryKey(autoGenerate = true)
    private int shoppingListID;
    @NonNull
    private String shoppingListName;

    public ShoppingList(@NonNull String shoppingListName) {
        this.shoppingListName = shoppingListName;
    }

    public int getShoppingListID() {
        return shoppingListID;
    }

    @NonNull
    public String getShoppingListName() {
        return shoppingListName;
    }

    public void setShoppingListName(@NonNull String shoppingListName) {
        this.shoppingListName = shoppingListName;
    }
}
