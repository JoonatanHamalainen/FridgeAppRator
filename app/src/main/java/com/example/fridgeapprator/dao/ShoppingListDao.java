package com.example.fridgeapprator.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fridgeapprator.model.ProductTypeWithProducts;
import com.example.fridgeapprator.model.ShoppingList;
import com.example.fridgeapprator.model.ShoppingListWithShoppingListProducts;

import java.util.List;

@Dao
public interface ShoppingListDao {

    @Insert
    void insert(ShoppingList shoppingList);

    @Query("SELECT * FROM shoppinglist")
    LiveData<List<ShoppingListWithShoppingListProducts>> getShoppingListsWithShoppingListProducts();

    @Query("SELECT * FROM shoppinglist WHERE shoppingListID = :shoppingListID")
    LiveData<List<ShoppingListWithShoppingListProducts>> getShoppingListWithItsProducts(int shoppingListID);
}
