package com.example.fridgeapprator.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shoppinglist")
public class ShoppingList {
    @NonNull
    private final String shoppingListName;
    @PrimaryKey(autoGenerate = true)
    private int shoppingListID;

    public ShoppingList(@NonNull String shoppingListName) {
        this.shoppingListName = shoppingListName;
    }

    public int getShoppingListID() {
        return shoppingListID;
    }

    public void setShoppingListID(int shoppingListID) {
        this.shoppingListID = shoppingListID;
    }

    @NonNull
    public String getShoppingListName() {
        return shoppingListName;
    }

}
