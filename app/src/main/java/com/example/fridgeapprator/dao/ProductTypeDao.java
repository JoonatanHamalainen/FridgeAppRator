package com.example.fridgeapprator.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.fridgeapprator.model.ProductType;
import com.example.fridgeapprator.model.ProductTypeWithProducts;

import java.util.List;

@Dao
public interface ProductTypeDao {

    @Insert
    long insert(ProductType productType);

    @Update
    void update (ProductType productType);

    @Delete
    void delete (ProductType productType);

    @Transaction
    @Query("SELECT * FROM producttype")
    LiveData<List<ProductTypeWithProducts>> getProductTypesWithProducts();


}
