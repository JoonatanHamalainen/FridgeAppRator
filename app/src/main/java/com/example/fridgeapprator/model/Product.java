package com.example.fridgeapprator.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Date;


@Entity(tableName = "product", foreignKeys = @ForeignKey(entity = ProductType.class, parentColumns = "productTypeID", childColumns = "productTypeID"))
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int productID;
    @NonNull
    private final Date expirationDate;

    @ColumnInfo(index = true)
    private final int productTypeID;


    public Product(int productTypeID, @NonNull Date expirationDate) {
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

    @NonNull
    public Date getExpirationDate() {
        return expirationDate;
    }

}
