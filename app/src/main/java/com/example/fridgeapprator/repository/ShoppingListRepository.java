package com.example.fridgeapprator.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.fridgeapprator.dao.ShoppingListDao;
import com.example.fridgeapprator.database.IHSDatabase;
import com.example.fridgeapprator.model.ShoppingList;
import com.example.fridgeapprator.model.ShoppingListWithShoppingListProducts;

import java.util.List;

public class ShoppingListRepository {
    private ShoppingListDao shoppingListDao;
    private LiveData<List<ShoppingListWithShoppingListProducts>> allShoppingListProducts;

    public ShoppingListRepository(Application application) {
        IHSDatabase db = IHSDatabase.getDatabase(application);
        shoppingListDao = db.shoppingListDao();
        allShoppingListProducts = shoppingListDao.getShoppingListsWithShoppingListProducts();
    }

    public LiveData<List<ShoppingListWithShoppingListProducts>> getAllShoppingListProducts() {
        return allShoppingListProducts;
    }


    public LiveData<ShoppingListWithShoppingListProducts> getShoppingListAndItsProducts(int shoppingListID) {
        return shoppingListDao.getShoppingListWithItsProducts(shoppingListID);
    }


    public void insert(ShoppingList shoppingList) {
        new insertAsyncTask(shoppingListDao).execute(shoppingList);
    }

    public void delete (ShoppingList shoppingList) {
        new deleteAsyncTask(shoppingListDao).execute(shoppingList);
    }

    private static class insertAsyncTask extends AsyncTask<ShoppingList, Void, Void> {

        private ShoppingListDao shoppingListAsyncTaskDao;

        insertAsyncTask(ShoppingListDao dao) {
            shoppingListAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(ShoppingList... shoppingLists) {
            shoppingListAsyncTaskDao.insert(shoppingLists[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<ShoppingList, Void, Void> {

        private ShoppingListDao shoppingListAsyncTaskDao;

        deleteAsyncTask(ShoppingListDao dao) {
            shoppingListAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(ShoppingList... shoppingLists) {
            shoppingListAsyncTaskDao.delete(shoppingLists[0]);
            return null;
        }
    }


}

