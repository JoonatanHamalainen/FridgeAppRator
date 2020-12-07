package com.example.fridgeapprator.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.fridgeapprator.model.ShoppingList;
import com.example.fridgeapprator.model.ShoppingListWithShoppingListProducts;

import java.util.List;

@Dao
public interface ShoppingListDao {

    @Insert
    void insert(ShoppingList shoppingList);
    @Delete
    void delete(ShoppingList shoppingList);


    @Transaction
    @Query("SELECT * FROM shoppinglist")
    LiveData<List<ShoppingListWithShoppingListProducts>> getShoppingListsWithShoppingListProducts();

    @Transaction
    @Query("SELECT * FROM shoppinglist WHERE shoppingListID = :shoppingListID")
    LiveData<ShoppingListWithShoppingListProducts> getShoppingListWithItsProducts(int shoppingListID);
}
