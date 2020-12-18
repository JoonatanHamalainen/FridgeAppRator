package com.example.fridgeapprator.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ProductTypeWithProducts {
    @Embedded
    public ProductType productType;

    @Relation(
            parentColumn = "productTypeID",
            entityColumn = "productTypeID"
    )

    public List<Product> products;

}
