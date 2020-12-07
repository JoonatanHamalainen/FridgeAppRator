package com.example.fridgeapprator.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fridgeapprator.model.ProductType;
import com.example.fridgeapprator.model.ProductTypeWithProducts;
import com.example.fridgeapprator.repository.ProductTypeRepository;

import java.util.List;

public class ProductTypeViewModel extends AndroidViewModel {
    private ProductTypeRepository productTypeRepository;
    private LiveData<List<ProductTypeWithProducts>> allProductTypes;


    public ProductTypeViewModel(Application application) {
        super(application);
        productTypeRepository = new ProductTypeRepository(application);
        allProductTypes = productTypeRepository.getAllProductTypes();
    }

    public LiveData<List<ProductTypeWithProducts>> getAllProductTypes() {
        return allProductTypes;
    }

    public LiveData<ProductTypeWithProducts> getProductTypeWithProducts(int productTypeID) {
        return productTypeRepository.getProductTypeWithProducts(productTypeID);
    }
    public void insert(ProductType productType) {
        productTypeRepository.insert(productType);
    }
    public void delete(ProductType productType) {
        productTypeRepository.delete(productType);
    }
    public void update(ProductType productType) {
        productTypeRepository.update(productType);
    }




}
