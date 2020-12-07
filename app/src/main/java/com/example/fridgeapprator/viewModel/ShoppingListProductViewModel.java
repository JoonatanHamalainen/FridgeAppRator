package com.example.fridgeapprator.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fridgeapprator.model.ShoppingListProduct;
import com.example.fridgeapprator.repository.ShoppingListProductRepository;

import java.util.List;

public class ShoppingListProductViewModel extends AndroidViewModel {

    private ShoppingListProductRepository shoppingListProductRepository;

    public ShoppingListProductViewModel(@NonNull Application application) {
        super(application);
        shoppingListProductRepository = new ShoppingListProductRepository(application);
    }

    public LiveData<ShoppingListProduct> getShoppingListProduct(int shoppingListProductID) {
        return shoppingListProductRepository.getShoppingListProduct(shoppingListProductID);
    }

    public LiveData<List<ShoppingListProduct>> getShoppingListProduct() {
        return shoppingListProductRepository.getAllShoppingListProducts();
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
