package com.example.fridgeapprator.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.fridgeapprator.dao.ProductTypeDao;
import com.example.fridgeapprator.database.IHSDatabase;
import com.example.fridgeapprator.model.ProductType;
import com.example.fridgeapprator.model.ProductTypeWithProducts;

import java.util.List;

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


    public void insert(ProductType p) {
        new insertAsyncTask(productTypeDao).execute(p);
    }


    private static class insertAsyncTask extends AsyncTask<ProductType, Void, Void> {

        private ProductTypeDao productTypeAsyncTaskDao;

        insertAsyncTask(ProductTypeDao dao) {
            productTypeAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(ProductType... productTypes) {
            productTypeAsyncTaskDao.insert(productTypes[0]);
            return null;
        }
    }



}
