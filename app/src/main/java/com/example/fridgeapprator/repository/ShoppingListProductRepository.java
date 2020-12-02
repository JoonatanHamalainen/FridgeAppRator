package com.example.fridgeapprator.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.fridgeapprator.dao.ProductTypeDao;
import com.example.fridgeapprator.dao.ShoppingListProductDao;
import com.example.fridgeapprator.database.IHSDatabase;
import com.example.fridgeapprator.model.ProductType;
import com.example.fridgeapprator.model.ShoppingListProduct;

import java.util.concurrent.ExecutionException;

public class ShoppingListProductRepository {
    private ShoppingListProductDao shoppingListProductDao;

    public ShoppingListProductRepository(Application application) {
        IHSDatabase db = IHSDatabase.getDatabase(application);
        shoppingListProductDao = db.shoppingListProductDao();

    }

    public ShoppingListProduct getShoppingListProduct(int id) throws ExecutionException, InterruptedException {
        return new getShoppingListProductAsyncTask(shoppingListProductDao).execute(id).get();
    }

    public void insert(ShoppingListProduct p) {
        new insertAsyncTask(shoppingListProductDao).execute(p);
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


    private static class getShoppingListProductAsyncTask extends AsyncTask<Integer, Void, ShoppingListProduct> {

        private ShoppingListProductDao shoppingListProductAsyncTaskDao;

        getShoppingListProductAsyncTask(ShoppingListProductDao dao) {
            shoppingListProductAsyncTaskDao = dao;
        }

        @Override
        protected ShoppingListProduct doInBackground(Integer... integers) {
            System.out.println("Shoppinglist thread: " + Thread.currentThread().getName());
            return shoppingListProductAsyncTaskDao.getShoppingListProduct(integers[0]);
        }
    }



}
