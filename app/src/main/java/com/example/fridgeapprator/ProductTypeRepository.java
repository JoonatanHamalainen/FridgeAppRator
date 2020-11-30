package com.example.fridgeapprator;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.fridgeapprator.IHSDatabase;
import com.example.fridgeapprator.ProductTypeDao;
import com.example.fridgeapprator.model.ProductType;
import com.example.fridgeapprator.model.ProductTypeWithProducts;

import java.util.List;

public class ProductTypeRepository {
    private ProductTypeDao productTypeDao;
    private LiveData<List<ProductTypeWithProducts>> allProductTypes;

    ProductTypeRepository(Application application) {
        IHSDatabase db = IHSDatabase.getDatabase(application);
        productTypeDao = db.productTypeDao();
        allProductTypes = productTypeDao.getProductTypesWithProducts();
    }

    LiveData<List<ProductTypeWithProducts>> getAllProductTypes() {
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
