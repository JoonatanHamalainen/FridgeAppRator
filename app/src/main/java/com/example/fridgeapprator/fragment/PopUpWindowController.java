package com.example.fridgeapprator.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fridgeapprator.R;
import com.example.fridgeapprator.adapter.ProductListAdapter;
import com.example.fridgeapprator.model.Product;
import com.example.fridgeapprator.model.ProductType;
import com.example.fridgeapprator.model.ProductTypeWithProducts;
import com.example.fridgeapprator.viewModel.ProductTypeViewModel;
import com.example.fridgeapprator.viewModel.ProductViewModel;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class PopUpWindowController {

    final Calendar myCalendar = Calendar.getInstance();

    public void openDateDialog(Context context, EditText editText) {
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            //myCalendar.get(Calendar.MONTH) returns January as zero and December as 11
            //so we need to normalize it before inserting it to the database
            int month = myCalendar.get(Calendar.MONTH) + 1;
            String calendarString = myCalendar.get(Calendar.YEAR) + "-" + month + "-" + myCalendar.get(Calendar.DATE);
            editText.setText(calendarString);
        };
        new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    @SuppressLint("ClickableViewAccessibility")
    //Creates a popup where you are able to view and delete expiration dates (products) of product type
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
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        int fridgeProductTypePosition = position;

        productTypeViewModel.getAllProductTypes().observe(owner, productTypes -> {
            try {
                productListAdapter.setProducts(productTypes.get(position).products);
                //closes an empty popup window
                if (productTypes.get(position).products.isEmpty()) {
                    popupWindow.dismiss();
                }
            } catch (Exception ignored) {
            }
        });


        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        productRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(activity, productRecyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                //Fridge product type position is the position of the product type clicked in FridgeProductListFragment.
                productViewModel.delete(Objects.requireNonNull(productTypeViewModel.getAllProductTypes().getValue()).get(fridgeProductTypePosition).products.get(position));
                ProductType productType = productTypeViewModel.getAllProductTypes().getValue().get(fridgeProductTypePosition).productType;
                //Product type amount is decreased in database when one of its products is deleted.
                productType.setAmount(productType.getAmount() - 1);
                productTypeViewModel.update(productType);

            }
        }));

        popupView.setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });
    }

    //Creates a popup where you are able to insert a product to the fridge
    public void insertSingleProductPopUp(View view, ViewGroup container, LayoutInflater inflater,
                                         FragmentActivity activity, LifecycleOwner owner) {

        ProductTypeViewModel productTypeViewModel = new ViewModelProvider(activity).get(ProductTypeViewModel.class);
        ProductViewModel productViewModel = new ViewModelProvider(activity).get(ProductViewModel.class);

        productTypeViewModel.getAllProductTypes().observe(owner, productTypes -> {
        });

        View popupView = inflater.inflate(R.layout.add_product_popup_window, container, false);
        ImageButton cancelButton = popupView.findViewById(R.id.buttonCancel);
        Button addNewProductButton = popupView.findViewById(R.id.buttonAddNewProduct);
        EditText newProductTypeName = popupView.findViewById(R.id.inputNewProductTypeName);
        EditText editText = popupView.findViewById(R.id.inputNewProductExpirationDate);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        cancelButton.setOnClickListener(view1 -> popupWindow.dismiss());


        editText.setOnClickListener(v -> openDateDialog(inflater.getContext(), editText));

        addNewProductButton.setOnClickListener(view12 -> {
            if (!newProductTypeName.getText().toString().equals("") && !editText.getText().toString().equals("")) {
                String newProductTypeNameValue = newProductTypeName.getText().toString();
                Date expirationDateValue = Date.valueOf(editText.getText().toString());
                List<ProductTypeWithProducts> productTypes = productTypeViewModel.getAllProductTypes().getValue();
                boolean found = false;
                ProductType productType = null;
                int newId;
                for (int i = 0; i < Objects.requireNonNull(productTypes).size(); i++) {
                    productType = productTypes.get(i).productType;
                    String name = productType.getProductTypeName();

                    if (name.equals(newProductTypeNameValue)) {
                        found = true;
                        break;
                    }
                }
                // if a product with the same name is not found in the fridge add it
                if (!found) {

                    newId = (int) productTypeViewModel.insert(new ProductType(newProductTypeNameValue, 1));
                    productViewModel.insert(new Product(newId, expirationDateValue));
                }
                //otherwise increase product type's amount by one and insert a product
                else {
                    productType.setAmount(productType.getAmount() + 1);
                    productViewModel.insert(new Product(productType.getProductTypeID(), expirationDateValue));
                    productTypeViewModel.update(productType);

                }
                popupWindow.dismiss();
            } else {
                Toast.makeText(inflater.getContext(), "Puuttuva pvm tai nimi", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @SuppressLint("ClickableViewAccessibility")
    //Creates a popup where you are able to insert a date and import a product to the fridge
    public void insertDatesPopUp(View view, ViewGroup container, LayoutInflater inflater,
                                 FragmentActivity activity, LifecycleOwner owner, String typeName) {

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

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        editText.setOnClickListener(v -> openDateDialog(inflater.getContext(), editText));

        add.setOnClickListener(v -> {
            if (!editText.getText().toString().equals("")) {
                List<ProductTypeWithProducts> productTypes = productTypeViewModel.getAllProductTypes().getValue();
                boolean found = false;
                ProductType productType = null;
                int newId;
                for (int i = 0; i < Objects.requireNonNull(productTypes).size(); i++) {
                    productType = productTypes.get(i).productType;
                    String name = productType.getProductTypeName();

                    if (name.equals(typeName)) {
                        found = true;
                        break;
                    }
                }
                Date expirationDate = Date.valueOf(editText.getText().toString());
                // if a product with the same name is not found in the fridge add it
                if (!found) {

                    newId = (int) productTypeViewModel.insert(new ProductType(typeName, 1));
                    productViewModel.insert(new Product(newId, expirationDate));
                }
                //otherwise increase product type's amount by one and insert a product
                else {
                    productType.setAmount(productType.getAmount() + 1);
                    productViewModel.insert(new Product(productType.getProductTypeID(), expirationDate));
                    productTypeViewModel.update(productType);

                }
                Toast.makeText(activity, "Tuote lisätty", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            } else {
                Toast.makeText(inflater.getContext(), "Puuttuva pvm", Toast.LENGTH_SHORT).show();
            }


        });

        skip.setOnClickListener(v -> {
            Toast.makeText(activity, "Tuotetta ei lisätty", Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();
        });


    }

}
