package com.example.fridgeapprator;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.fridgeapprator.model.Product;

import java.util.List;

public class ProductRepository {
    private ProductDao productDao;
    private LiveData<List<Product>> allProducts;

    ProductRepository(Application application) {
        IHSDatabase db = IHSDatabase.getDatabase(application);
        productDao = db.productDao();
        allProducts = productDao.getAllProducts();
    }

    LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public void insert(Product p) {
        new insertAsyncTask(productDao).execute(p);
    }
    public AsyncTask<Integer, Void, Product> getProduct(int id) {
        return new getProd1AsyncTask(productDao).execute(id);
    }

    //

    private static class insertAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDao productAsyncTaskDao;

        insertAsyncTask(ProductDao dao) {
            productAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productAsyncTaskDao.insert(products[0]);
            return null;
        }
    }

    private static class getProd1AsyncTask extends AsyncTask<Integer, Void, Product> {

        private ProductDao productAsyncTaskDao;

        getProd1AsyncTask(ProductDao dao) {
            productAsyncTaskDao = dao;
        }


        @Override
        protected Product doInBackground(Integer... integers) {
            return productAsyncTaskDao.getProduct(integers[0]);
        }
    }


}
