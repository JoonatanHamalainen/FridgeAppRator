package com.example.fridgeapprator.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fridgeapprator.model.ShoppingListProduct;

import java.util.List;

@Dao
public interface ShoppingListProductDao {

    @Insert
    void insert(ShoppingListProduct shoppingListProduct);

    @Query("SELECT * from shoppinglistproduct where shoppingListProductID = :shoppingListProductID")
    ShoppingListProduct getShoppingListProduct(int shoppingListProductID);

    @Query("SELECT * from shoppinglistproduct")
    LiveData<List<ShoppingListProduct>> getAllShoppingListProducts();
}
