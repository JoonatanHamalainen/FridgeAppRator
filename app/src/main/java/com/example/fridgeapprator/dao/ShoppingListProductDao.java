package com.example.fridgeapprator.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ShoppingListProductDao {

    @Insert
    void insert(ShoppingListProductDao shoppingListProductDao);

    @Query("SELECT * from shoppinglistproduct where shoppingListProductID = :shoppingListProductID")
    ShoppingListProductDao getShoppingListProduct(int shoppingListProductID);

    @Query("SELECT * from shoppinglistproduct")
    LiveData<List<ShoppingListProductDao>> getAllShoppingListProducts();
}
