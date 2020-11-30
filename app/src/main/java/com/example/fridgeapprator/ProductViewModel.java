package com.example.fridgeapprator;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fridgeapprator.model.Product;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class ProductViewModel extends AndroidViewModel {

    private ProductRepository productRepository;
    private LiveData<List<Product>> allProducts;

    public ProductViewModel(Application application) {
        super(application);
        productRepository = new ProductRepository(application);
        allProducts = productRepository.getAllProducts();
    }

    LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public Product getProduct(int id) throws ExecutionException, InterruptedException {
        return productRepository.getProduct(id).get();
    }

    public void insert(Product p) {
        productRepository.insert(p);
    }


}
