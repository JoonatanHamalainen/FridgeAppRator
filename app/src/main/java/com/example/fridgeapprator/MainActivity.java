package com.example.fridgeapprator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.fridgeapprator.model.Product;
import com.example.fridgeapprator.model.ProductType;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ProductTypeViewModel productTypeViewModel;
    ProductViewModel productViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productTypeViewModel = new ViewModelProvider(this).get(ProductTypeViewModel.class);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productTypeViewModel.insert(new ProductType("Maito", 1));
        productViewModel.insert(new Product("Valion maito", 1));

        try {
            System.out.println(productViewModel.getProduct(1).getProductTypeID());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < productViewModel.getAllProducts().getValue().size(); i++) {
            System.out.println(productViewModel.getAllProducts().getValue().get(i).getProductName());
        }


        for (int i = 0; i < productTypeViewModel.getAllProductTypes().getValue().size(); i++) {
            System.out.println(productTypeViewModel.getAllProductTypes().getValue().get(i));
        }




    }
}