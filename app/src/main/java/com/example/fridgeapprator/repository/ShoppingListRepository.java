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
    private final ShoppingListDao shoppingListDao;
    private final LiveData<List<ShoppingListWithShoppingListProducts>> allShoppingListProducts;

    public ShoppingListRepository(Application application) {
        IHSDatabase db = IHSDatabase.getDatabase(application);
        shoppingListDao = db.shoppingListDao();
        allShoppingListProducts = shoppingListDao.getShoppingListsWithShoppingListProducts();
    }

    //not in use! will be in use when multiple shoppinglists are implemented
    @SuppressWarnings("unused")
    public LiveData<List<ShoppingListWithShoppingListProducts>> getAllShoppingListProducts() {
        return allShoppingListProducts;
    }


    public LiveData<ShoppingListWithShoppingListProducts> getShoppingListAndItsProducts(int shoppingListID) {
        return shoppingListDao.getShoppingListWithItsProducts(shoppingListID);
    }

    @SuppressWarnings("deprecation")
    public void insert(ShoppingList shoppingList) {
        new insertAsyncTask(shoppingListDao).execute(shoppingList);
    }
    @SuppressWarnings("deprecation")
    public void delete (ShoppingList shoppingList) {
        new deleteAsyncTask(shoppingListDao).execute(shoppingList);
    }
    @SuppressWarnings("deprecation")
    private static class insertAsyncTask extends AsyncTask<ShoppingList, Void, Void> {

        private final ShoppingListDao shoppingListAsyncTaskDao;

        insertAsyncTask(ShoppingListDao dao) {
            shoppingListAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(ShoppingList... shoppingLists) {
            shoppingListAsyncTaskDao.insert(shoppingLists[0]);
            return null;
        }
    }
    @SuppressWarnings("deprecation")
    private static class deleteAsyncTask extends AsyncTask<ShoppingList, Void, Void> {

        private final ShoppingListDao shoppingListAsyncTaskDao;

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

