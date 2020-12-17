package com.example.fridgeapprator.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.fridgeapprator.model.ShoppingListProduct;

@Dao
public interface ShoppingListProductDao {

    @Insert
    void insert(ShoppingListProduct shoppingListProduct);
    @Delete
    void delete(ShoppingListProduct shoppingListProduct);
    @Update
    void update(ShoppingListProduct shoppingListProduct);

}
