package com.example.fridgeapprator.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fridgeapprator.model.Product;
import com.example.fridgeapprator.repository.ProductRepository;

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

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }


    public Product getProductById(int id) throws InterruptedException, ExecutionException {
        return productRepository.getProductById(id);
    }

    public String getProductByIdTest(int id) throws InterruptedException, ExecutionException {
        return productRepository.getProductByIdTest(id).join();
    }
    public String getProductByIdTest2(int id) throws InterruptedException, ExecutionException {
        return productRepository.getProductByIdTest2(id).join();
    }

    public void insert(Product p) {
        productRepository.insert(p);
    }


}
