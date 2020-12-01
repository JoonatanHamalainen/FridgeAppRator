package com.example.fridgeapprator.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.fridgeapprator.dao.ProductDao;
import com.example.fridgeapprator.database.IHSDatabase;
import com.example.fridgeapprator.model.Product;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProductRepository {
    private ProductDao productDao;
    private LiveData<List<Product>> allProducts;

    public ProductRepository(Application application) {
        IHSDatabase db = IHSDatabase.getDatabase(application);
        productDao = db.productDao();
        allProducts = productDao.getAllProducts();
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public void insert(Product p) {
        new insertAsyncTask(productDao).execute(p);
    }
    public Product getProduct(int id) throws ExecutionException, InterruptedException {
        return new getProd1AsyncTask(productDao).execute(id).get();
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
