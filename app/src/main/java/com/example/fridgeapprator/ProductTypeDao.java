package com.example.fridgeapprator;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.fridgeapprator.model.ProductType;
import com.example.fridgeapprator.model.ProductTypeWithProducts;

import java.util.List;

@Dao
public interface ProductTypeDao {

    @Insert
    void insert(ProductType productType);

    @Transaction
    @Query("SELECT * FROM producttype")
     LiveData<List<ProductTypeWithProducts>> getProductTypesWithProducts();

}
