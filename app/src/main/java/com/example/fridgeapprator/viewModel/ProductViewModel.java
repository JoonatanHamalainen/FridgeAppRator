package com.example.fridgeapprator.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.fridgeapprator.model.Product;
import com.example.fridgeapprator.repository.ProductRepository;




public class ProductViewModel extends AndroidViewModel {

    private final ProductRepository productRepository;

    public ProductViewModel(Application application) {
        super(application);
        productRepository = new ProductRepository(application);
    }


    public void delete(Product product) {productRepository.delete(product);}


    public void insert(Product product) {
        productRepository.insert(product);
    }


}
