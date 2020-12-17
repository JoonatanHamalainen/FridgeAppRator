package com.example.fridgeapprator.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.fridgeapprator.model.ShoppingListProduct;
import com.example.fridgeapprator.repository.ShoppingListProductRepository;


public class ShoppingListProductViewModel extends AndroidViewModel {

    private final ShoppingListProductRepository shoppingListProductRepository;


    public ShoppingListProductViewModel(@NonNull Application application) {
        super(application);
        shoppingListProductRepository = new ShoppingListProductRepository(application);
    }


    public void insert(ShoppingListProduct shoppingListProduct) {
        shoppingListProductRepository.insert(shoppingListProduct);
    }

    public void delete(ShoppingListProduct shoppingListProduct) {
        shoppingListProductRepository.delete(shoppingListProduct);
    }

    public void update(ShoppingListProduct shoppingListProduct) {
        shoppingListProductRepository.update(shoppingListProduct);
    }


}
