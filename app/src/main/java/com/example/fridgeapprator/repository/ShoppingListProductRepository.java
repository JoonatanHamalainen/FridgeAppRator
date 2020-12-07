package com.example.fridgeapprator.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.fridgeapprator.dao.ShoppingListProductDao;
import com.example.fridgeapprator.database.IHSDatabase;
import com.example.fridgeapprator.model.ShoppingListProduct;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ShoppingListProductRepository {
    private ShoppingListProductDao shoppingListProductDao;

    public ShoppingListProductRepository(Application application) {
        IHSDatabase db = IHSDatabase.getDatabase(application);
        shoppingListProductDao = db.shoppingListProductDao();

    }

    public LiveData<ShoppingListProduct> getShoppingListProduct(int shoppingListProductID) {
        return shoppingListProductDao.getShoppingListProduct(shoppingListProductID);
    }

    public LiveData<List<ShoppingListProduct>> getAllShoppingListProducts() {
        return shoppingListProductDao.getAllShoppingListProducts();
    }


    public void insert(ShoppingListProduct shoppingListProduct) {
        new insertAsyncTask(shoppingListProductDao).execute(shoppingListProduct);
    }

    public void delete(ShoppingListProduct shoppingListProduct) {
        new deleteAsyncTask(shoppingListProductDao).execute(shoppingListProduct);
    }

    public void update(ShoppingListProduct shoppingListProduct) {
        new updateAsyncTask(shoppingListProductDao).execute(shoppingListProduct);
    }

    private static class insertAsyncTask extends AsyncTask<ShoppingListProduct, Void, Void> {
        private ShoppingListProductDao shoppingListProductAsyncTaskDao;

        insertAsyncTask(ShoppingListProductDao dao) {
            shoppingListProductAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ShoppingListProduct... shoppingListProducts) {
            shoppingListProductAsyncTaskDao.insert(shoppingListProducts[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<ShoppingListProduct, Void, Void> {
        private ShoppingListProductDao shoppingListProductAsyncTaskDao;

        deleteAsyncTask(ShoppingListProductDao dao) {
            shoppingListProductAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ShoppingListProduct... shoppingListProducts) {
            shoppingListProductAsyncTaskDao.delete(shoppingListProducts[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<ShoppingListProduct, Void, Void> {
        private ShoppingListProductDao shoppingListProductAsyncTaskDao;

        updateAsyncTask(ShoppingListProductDao dao) {
            shoppingListProductAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ShoppingListProduct... shoppingListProducts) {
            shoppingListProductAsyncTaskDao.update(shoppingListProducts[0]);
            return null;
        }
    }




}
