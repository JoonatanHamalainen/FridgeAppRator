package com.example.fridgeapprator.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import com.example.fridgeapprator.model.Product;

@Dao
public interface ProductDao {

    @Insert
    void insert (Product product);
    @Delete
    void delete (Product product);


}
