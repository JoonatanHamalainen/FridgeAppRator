package com.example.fridgeapprator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.fridgeapprator.model.Product;
import com.example.fridgeapprator.model.ProductType;
import com.example.fridgeapprator.model.ShoppingList;
import com.example.fridgeapprator.model.ShoppingListProduct;
import com.example.fridgeapprator.viewModel.ProductTypeViewModel;
import com.example.fridgeapprator.viewModel.ProductViewModel;
import com.example.fridgeapprator.viewModel.ShoppingListProductViewModel;
import com.example.fridgeapprator.viewModel.ShoppingListViewModel;

import java.sql.Date;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ProductTypeViewModel productTypeViewModel;
    ProductViewModel productViewModel;
    ShoppingListProductViewModel shoppingListProductViewModel;
    ShoppingListViewModel shoppingListViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productTypeViewModel = new ViewModelProvider(this).get(ProductTypeViewModel.class);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productTypeViewModel.insert(new ProductType("Maito", 1));
        productViewModel.insert(new Product(1, new Date(2020-11-28)));
        shoppingListViewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
        shoppingListViewModel.insert(new ShoppingList("Joonatananin kassit"));
        shoppingListProductViewModel = new ViewModelProvider(this).get(ShoppingListProductViewModel.class);
        shoppingListProductViewModel.insert(new ShoppingListProduct("Maito", 5, 1));


        try {
            System.out.println(shoppingListProductViewModel.getShoppingListProduct(1).getProductTypeName());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < productViewModel.getAllProducts().getValue().size(); i++) {
            System.out.println(productViewModel.getAllProducts().getValue().get(i).getExpirationDate());
        }


        for (int i = 0; i < productTypeViewModel.getAllProductTypes().getValue().size(); i++) {
            System.out.println(productTypeViewModel.getAllProductTypes().getValue().get(i));
        }




    }
}