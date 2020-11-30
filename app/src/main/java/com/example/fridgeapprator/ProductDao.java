package com.example.fridgeapprator;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fridgeapprator.model.Product;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insert (Product product);

    @Query("SELECT * from product where productID = :productID")
    Product getProduct(int productID);

    @Query("SELECT * from product ORDER BY productName ASC")
    LiveData<List<Product>> getAllProducts();

}
