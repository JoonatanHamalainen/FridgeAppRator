package com.example.fridgeapprator.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.fridgeapprator.dao.ProductDao;
import com.example.fridgeapprator.database.IHSDatabase;
import com.example.fridgeapprator.model.Product;


public class ProductRepository {
    private final ProductDao productDao;

    public ProductRepository(Application application) {
        IHSDatabase db = IHSDatabase.getDatabase(application);
        productDao = db.productDao();
    }


    @SuppressWarnings("deprecation")
    public void insert(Product product) {
        new insertAsyncTask(productDao).execute(product);
    }

    @SuppressWarnings("deprecation")
    public void delete(Product product) {
        new deleteAsyncTask(productDao).execute(product);
    }

    @SuppressWarnings("deprecation")
    private static class deleteAsyncTask extends AsyncTask<Product, Void, Void> {

        private final ProductDao productAsyncTaskDao;

        deleteAsyncTask(ProductDao dao) {
            productAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productAsyncTaskDao.delete(products[0]);
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    private static class insertAsyncTask extends AsyncTask<Product, Void, Void> {

        private final ProductDao productAsyncTaskDao;

        insertAsyncTask(ProductDao dao) {
            productAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productAsyncTaskDao.insert(products[0]);
            return null;
        }
    }


}
