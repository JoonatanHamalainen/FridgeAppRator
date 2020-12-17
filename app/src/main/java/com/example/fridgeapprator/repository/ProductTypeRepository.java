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
    private final ProductTypeDao productTypeDao;
    private final LiveData<List<ProductTypeWithProducts>> allProductTypes;

    public ProductTypeRepository(Application application) {
        IHSDatabase db = IHSDatabase.getDatabase(application);
        productTypeDao = db.productTypeDao();
        allProductTypes = productTypeDao.getProductTypesWithProducts();
    }

    public LiveData<List<ProductTypeWithProducts>> getAllProductTypes() {
        return allProductTypes;
    }


    @SuppressWarnings("deprecation")
    public long insert(ProductType productType) {
        try {
            return new insertAsyncTask(productTypeDao).execute(productType).get();
        } catch (ExecutionException | InterruptedException e) {
            return 0;
        }

    }

    @SuppressWarnings("deprecation")
    public void delete(ProductType productType) {
        new deleteAsyncTask(productTypeDao).execute(productType);
    }
    @SuppressWarnings("deprecation")
    public void update(ProductType productType) {
        new updateAsyncTask(productTypeDao).execute(productType);
    }


    @SuppressWarnings("deprecation")
    private static class insertAsyncTask extends AsyncTask<ProductType, Long, Long> {
        private final ProductTypeDao productTypeAsyncTaskDao;
        insertAsyncTask(ProductTypeDao dao) {
            productTypeAsyncTaskDao = dao;
        }
        @Override
        protected Long doInBackground(ProductType... productTypes) {
            return productTypeAsyncTaskDao.insert(productTypes[0]);

        }

    }

    @SuppressWarnings("deprecation")
    private static class updateAsyncTask extends AsyncTask<ProductType, Void, Void> {
        private final ProductTypeDao productTypeAsyncTaskDao;
        updateAsyncTask(ProductTypeDao dao) {
            productTypeAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(ProductType... productTypes) {
            productTypeAsyncTaskDao.update(productTypes[0]);
            return null;
        }

    }


    @SuppressWarnings("deprecation")
    private static class deleteAsyncTask extends AsyncTask<ProductType, Void, Void> {
        private final ProductTypeDao productTypeAsyncTaskDao;
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
