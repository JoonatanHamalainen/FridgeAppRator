package com.example.fridgeapprator.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fridgeapprator.model.ShoppingList;
import com.example.fridgeapprator.model.ShoppingListWithShoppingListProducts;
import com.example.fridgeapprator.repository.ShoppingListRepository;

import java.util.List;

public class ShoppingListViewModel extends AndroidViewModel {

    private final ShoppingListRepository shoppingListRepository;
    private LiveData<ShoppingListWithShoppingListProducts> allShoppingListProducts;

    public ShoppingListViewModel(Application application) {
        super(application);
        shoppingListRepository = new ShoppingListRepository(application);
        allShoppingListProducts = shoppingListRepository.getShoppingListAndItsProducts(1);
    }

    public LiveData<ShoppingListWithShoppingListProducts> getShoppingListWithItsProducts(int shoppingListID) {
        return shoppingListRepository.getShoppingListAndItsProducts(shoppingListID);
    }

    public LiveData<ShoppingListWithShoppingListProducts> getAllShoppingListProducts() {
        return allShoppingListProducts;
    }

    public void delete(ShoppingList shoppingList) {
        shoppingListRepository.delete(shoppingList);
    }
    public void insert(ShoppingList shoppingList) {shoppingListRepository.insert(shoppingList);}
}
