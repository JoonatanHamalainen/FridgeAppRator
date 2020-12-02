package com.example.fridgeapprator.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.fridgeapprator.dao.ProductDao;
import com.example.fridgeapprator.database.IHSDatabase;
import com.example.fridgeapprator.model.Product;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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

    public Product getProductById(int id) {
        return CompletableFuture.supplyAsync(() -> productDao.getProduct(id)).join();

    }


    public CompletableFuture<String> getProductByIdTest(int id) throws InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Threadi test1: " + Thread.currentThread().getName());

            try {


                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "Result of the asynchronous computation";
        }).thenApply(result -> {
            System.out.println("Threadi test1 then apply: " + Thread.currentThread().getName());

            System.out.println(result);
            return "Result of the then apply";
        });

    }


    public CompletableFuture<String> getProductByIdTest2(int id) throws InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Threadi test2: " + Thread.currentThread().getName());
            return "Result of the asynchronous computation in test2";
        }).thenApply(result -> {
            System.out.println("Threadi test2 then aplly: " + Thread.currentThread().getName());
            System.out.println(result);
            return "Result of the then apply in test 2";
        });
    }


}
