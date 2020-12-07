package com.example.fridgeapprator.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fridgeapprator.model.Product;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insert (Product product);
    @Delete
    void delete (Product product);

    //Query on async jos se palauttaa livedataa, singlee, maybe ja jtn outoi.
    //insert ei ole async joten se pitää ite asyncroisoidaa.
    @Query("SELECT * from product where productID = :productID")
    LiveData<Product> getProduct(int productID);

    @Query("SELECT * from product")
    LiveData<List<Product>> getAllProducts();

}
