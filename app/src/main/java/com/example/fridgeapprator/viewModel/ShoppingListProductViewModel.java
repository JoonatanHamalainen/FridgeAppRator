package com.example.fridgeapprator.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.fridgeapprator.model.ShoppingListProduct;
import com.example.fridgeapprator.repository.ShoppingListProductRepository;

import java.util.concurrent.ExecutionException;

public class ShoppingListProductViewModel extends AndroidViewModel {

    private ShoppingListProductRepository shoppingListProductRepository;


    public ShoppingListProductViewModel(@NonNull Application application) {
        super(application);
        shoppingListProductRepository = new ShoppingListProductRepository(application);

    }

    public void insert(ShoppingListProduct p) {
        shoppingListProductRepository.insert(p);
    }

    public ShoppingListProduct getShoppingListProduct(int id) throws ExecutionException, InterruptedException {
        return shoppingListProductRepository.getShoppingListProduct(id);
    }

}
