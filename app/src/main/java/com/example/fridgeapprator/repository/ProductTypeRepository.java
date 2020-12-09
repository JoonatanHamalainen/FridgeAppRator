package com.example.fridgeapprator.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.fridgeapprator.dao.ProductTypeDao;
import com.example.fridgeapprator.database.IHSDatabase;
import com.example.fridgeapprator.model.ProductType;
import com.example.fridgeapprator.model.ProductTypeWithProducts;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProductTypeRepository {
    private ProductTypeDao productTypeDao;
    private LiveData<List<ProductTypeWithProducts>> allProductTypes;

    public ProductTypeRepository(Application application) {
        IHSDatabase db = IHSDatabase.getDatabase(application);
        productTypeDao = db.productTypeDao();
        allProductTypes = productTypeDao.getProductTypesWithProducts();
    }

    public LiveData<List<ProductTypeWithProducts>> getAllProductTypes() {
        return allProductTypes;
    }

    public LiveData<ProductTypeWithProducts> getProductTypeWithProducts(int productTypeID) {
        return productTypeDao.getProductTypeWithProducts(productTypeID);
    }


    public long insert(ProductType productType) {
        try {
            return new insertAsyncTask(productTypeDao).execute(productType).get();
        } catch (ExecutionException | InterruptedException e) {
            return 0;
        }

    }

    public void delete(ProductType productType) {
        new deleteAsyncTask(productTypeDao).execute(productType);
    }
    public void update(ProductType productType) {
        new updateAsyncTask(productTypeDao).execute(productType);
    }


    private static class insertAsyncTask extends AsyncTask<ProductType, Long, Long> {
        private ProductTypeDao productTypeAsyncTaskDao;
        insertAsyncTask(ProductTypeDao dao) {
            productTypeAsyncTaskDao = dao;
        }
        @Override
        protected Long doInBackground(ProductType... productTypes) {
            return productTypeAsyncTaskDao.insert(productTypes[0]);

        }

    }

    private static class updateAsyncTask extends AsyncTask<ProductType, Void, Void> {
        private ProductTypeDao productTypeAsyncTaskDao;
        updateAsyncTask(ProductTypeDao dao) {
            productTypeAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(ProductType... productTypes) {
            productTypeAsyncTaskDao.update(productTypes[0]);
            return null;
        }

    }


    private static class deleteAsyncTask extends AsyncTask<ProductType, Void, Void> {
        private ProductTypeDao productTypeAsyncTaskDao;
        deleteAsyncTask(ProductTypeDao dao) {
            productTypeAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(ProductType... productTypes) {
            productTypeAsyncTaskDao.delete(productTypes[0]);
            return null;
        }
    }



}
