package com.example.fridgeapprator.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;


@Entity(tableName = "product")
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int productID;
    @NonNull
    private Date expirationDate;

    private int productTypeID;

    public Product(int productTypeID, Date expirationDate) {
        this.productTypeID = productTypeID;
        this.expirationDate = expirationDate;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(int productTypeID) {
        this.productTypeID = productTypeID;
    }

    @NonNull
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(@NonNull Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
