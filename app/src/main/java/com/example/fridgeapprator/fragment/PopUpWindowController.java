package com.example.fridgeapprator.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fridgeapprator.R;
import com.example.fridgeapprator.model.Product;
import com.example.fridgeapprator.model.ProductType;
import com.example.fridgeapprator.model.ProductTypeWithProducts;
import com.example.fridgeapprator.viewModel.ProductTypeViewModel;
import com.example.fridgeapprator.viewModel.ProductViewModel;
import com.example.fridgeapprator.viewModel.ShoppingListProductViewModel;
import com.example.fridgeapprator.viewModel.ShoppingListViewModel;

import java.sql.Date;
import java.util.List;

public class PopUpWindowController {

    public void showProductTypeProductsPopUp(View view, ViewGroup container, int position, LayoutInflater inflater,
                                             FragmentActivity activity, LifecycleOwner owner) {

        View popupView = inflater.inflate(R.layout.show_products_popup_window, container, false);
        ProductListAdapter productListAdapter = new ProductListAdapter(inflater.getContext());
        ProductTypeViewModel productTypeViewModel = new ViewModelProvider(activity).get(ProductTypeViewModel.class);
        ProductViewModel productViewModel = new ViewModelProvider(activity).get(ProductViewModel.class);
        RecyclerView productRecyclerView = popupView.findViewById(R.id.showProductsRecyclerView);
        LinearLayoutManager llm2 = new LinearLayoutManager(activity);
        productRecyclerView.setLayoutManager(llm2);
        productRecyclerView.setAdapter(productListAdapter);

        int fridgeProductTypePosition = position;

        productTypeViewModel.getAllProductTypes().observe(owner, productTypes -> {
            try {
                productListAdapter.setProducts(productTypes.get(position).products);
            } catch (Exception e) {
            }
        });

        productViewModel.getAllProducts().observe(owner, products -> {

        });

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        productRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(activity, productRecyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                productViewModel.delete(productViewModel.getAllProducts().getValue().get(position));
                ProductType productType = productTypeViewModel.getAllProductTypes().getValue().get(fridgeProductTypePosition).productType;
                productType.setAmount(productType.getAmount()-1);
                productTypeViewModel.update(productType);

            }
        }));

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public void insertDatesPopUp(View view, ViewGroup container, LayoutInflater inflater,
                                             FragmentActivity activity, LifecycleOwner owner, String typeName, int amount) {

        View popupView = inflater.inflate(R.layout.enter_expirationdate_popup_window, container, false);
        ProductTypeViewModel productTypeViewModel = new ViewModelProvider(activity).get(ProductTypeViewModel.class);
        ProductViewModel productViewModel = new ViewModelProvider(activity).get(ProductViewModel.class);

        Button add = popupView.findViewById(R.id.buttonAddProductPopUp);
        Button skip = popupView.findViewById(R.id.buttonSkipAddProductPopUp);
        EditText editText = popupView.findViewById(R.id.inputExpirationDatePopUp);
        TextView textView = popupView.findViewById(R.id.textViewHeaderPopUpExpiration);
        textView.setText(typeName);

        productTypeViewModel.getAllProductTypes().observe(owner, productTypes -> {
        });


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<ProductTypeWithProducts> productTypes = productTypeViewModel.getAllProductTypes().getValue();
                boolean found = false;
                ProductType productType = null;
                int newId = 0;
                for(int i = 0; i < productTypes.size(); i++) {
                    productType = productTypes.get(i).productType;
                    String name = productType.getProductTypeName();

                    if(name.equals(typeName)) {
                        found = true;
                        break;
                    }
                }
                Date expirationDateValue = Date.valueOf(editText.getText().toString());
                if (!found) {

                    newId = (int)productTypeViewModel.insert(new ProductType(typeName, 1));

                    List<ProductTypeWithProducts> updatedProductTypes = productTypeViewModel.getAllProductTypes().getValue();
                    productViewModel.insert(new Product(newId, expirationDateValue));
                }
                else {
                    productType.setAmount(productType.getAmount() + 1);
                    productViewModel.insert(new Product(productType.getProductTypeID(), expirationDateValue));
                    productTypeViewModel.update(productType);

                }
                Toast.makeText(activity, "Tuote lisätty", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();

            }
        });

        skip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Tuotetta ei lisätty", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });



        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });



    }




}
