package com.example.fridgeapprator.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.fridgeapprator.dao.ProductDao;
import com.example.fridgeapprator.database.IHSDatabase;
import com.example.fridgeapprator.model.Product;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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

    public LiveData<Product> getProductById(int id) {
        return productDao.getProduct(id);
    }

    public void insert(Product product) {
        new insertAsyncTask(productDao).execute(product);
    }

    public void delete (Product product) {
        new deleteAsyncTask(productDao).execute(product);
    }

    private static class deleteAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDao productAsyncTaskDao;

        deleteAsyncTask(ProductDao dao) {
            productAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productAsyncTaskDao.delete(products[0]);
            return null;
        }
    }

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



}
