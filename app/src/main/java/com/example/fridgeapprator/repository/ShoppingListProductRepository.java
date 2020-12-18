package com.example.fridgeapprator.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.fridgeapprator.dao.ShoppingListProductDao;
import com.example.fridgeapprator.database.IHSDatabase;
import com.example.fridgeapprator.model.ShoppingListProduct;


public class ShoppingListProductRepository {
    private final ShoppingListProductDao shoppingListProductDao;

    public ShoppingListProductRepository(Application application) {
        IHSDatabase db = IHSDatabase.getDatabase(application);
        shoppingListProductDao = db.shoppingListProductDao();

    }


    @SuppressWarnings("deprecation")
    public void insert(ShoppingListProduct shoppingListProduct) {
        new insertAsyncTask(shoppingListProductDao).execute(shoppingListProduct);
    }

    @SuppressWarnings("deprecation")
    public void delete(ShoppingListProduct shoppingListProduct) {
        new deleteAsyncTask(shoppingListProductDao).execute(shoppingListProduct);
    }

    @SuppressWarnings("deprecation")
    public void update(ShoppingListProduct shoppingListProduct) {
        new updateAsyncTask(shoppingListProductDao).execute(shoppingListProduct);
    }

    @SuppressWarnings("deprecation")
    private static class insertAsyncTask extends AsyncTask<ShoppingListProduct, Void, Void> {
        private final ShoppingListProductDao shoppingListProductAsyncTaskDao;

        insertAsyncTask(ShoppingListProductDao dao) {
            shoppingListProductAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ShoppingListProduct... shoppingListProducts) {
            shoppingListProductAsyncTaskDao.insert(shoppingListProducts[0]);
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    private static class deleteAsyncTask extends AsyncTask<ShoppingListProduct, Void, Void> {
        private final ShoppingListProductDao shoppingListProductAsyncTaskDao;

        deleteAsyncTask(ShoppingListProductDao dao) {
            shoppingListProductAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ShoppingListProduct... shoppingListProducts) {
            shoppingListProductAsyncTaskDao.delete(shoppingListProducts[0]);
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    private static class updateAsyncTask extends AsyncTask<ShoppingListProduct, Void, Void> {
        private final ShoppingListProductDao shoppingListProductAsyncTaskDao;

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
