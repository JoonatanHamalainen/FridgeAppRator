package com.example.fridgeapprator.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ShoppingListWithShoppingListProducts {

    @Embedded
    public ShoppingList shoppingList;

    @Relation (
            parentColumn = "shoppingListID",
            entityColumn = "shoppingListID"
    )

    public List<ShoppingListProduct> shoppingListProducts;
}
