package com.example.fridgeapprator.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.zip.Inflater;

public class PopUpWindowController {

    final Calendar myCalendar = Calendar.getInstance();
    EditText editText = null;

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            int month = myCalendar.get(Calendar.MONTH) + 1;
            String calendarString = myCalendar.get(Calendar.YEAR) + "-" + month + "-" + myCalendar.get(Calendar.DATE);
            editText.setText(calendarString);
        }

    };

    public void openDateDialog(Context context) {
        new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }


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

        //this variable represents the position of the productType object at the position of the click  in the fridge window.
        int fridgeProductTypePosition = position;

        productTypeViewModel.getAllProductTypes().observe(owner, productTypes -> {
            try {
                productListAdapter.setProducts(productTypes.get(position).products);
            } catch (Exception e) {
            }
        });

        //ehkä ongelma
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
                // tää rivi oli huono rivi! sen takia ei toiminu poisto.
                // productViewModel.delete(productViewModel.getAllProducts().getValue().get(position));
                //
                productViewModel.delete(productTypeViewModel.getAllProductTypes().getValue().get(fridgeProductTypePosition).products.get(position));
                ProductType productType = productTypeViewModel.getAllProductTypes().getValue().get(fridgeProductTypePosition).productType;
                productType.setAmount(productType.getAmount() - 1);
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

    public void insertSingleProductPopUp(View view, ViewGroup container, LayoutInflater inflater,
                                         FragmentActivity activity, LifecycleOwner owner) {

        ProductTypeViewModel productTypeViewModel = new ViewModelProvider(activity).get(ProductTypeViewModel.class);
        ProductViewModel productViewModel = new ViewModelProvider(activity).get(ProductViewModel.class);

        productTypeViewModel.getAllProductTypes().observe(owner, productTypes -> {
        });

        // inflate the layout of the popup window
        View popupView = inflater.inflate(R.layout.add_product_popup_window, container, false);
        ImageButton cancelButton = popupView.findViewById(R.id.buttonCancel);
        Button addNewProductButton = popupView.findViewById(R.id.buttonAddNewProduct);
        EditText newProductTypeName = popupView.findViewById(R.id.inputNewProductTypeName);
        editText = popupView.findViewById(R.id.inputNewProductExpirationDate);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });


        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                openDateDialog(inflater.getContext());
            }
        });

        addNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!newProductTypeName.getText().toString().equals("") && !editText.getText().toString().equals("")) {
                    String newProductTypeNameValue = newProductTypeName.getText().toString();
                    Date expirationDateValue = Date.valueOf(editText.getText().toString());
                    List<ProductTypeWithProducts> productTypes = productTypeViewModel.getAllProductTypes().getValue();
                    boolean found = false;
                    ProductType productType = null;
                    int newId = 0;
                    for (int i = 0; i < productTypes.size(); i++) {
                        productType = productTypes.get(i).productType;
                        String name = productType.getProductTypeName();

                        if (name.equals(newProductTypeNameValue)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {

                        newId = (int) productTypeViewModel.insert(new ProductType(newProductTypeNameValue, 1));

                        List<ProductTypeWithProducts> updatedProductTypes = productTypeViewModel.getAllProductTypes().getValue();
                        productViewModel.insert(new Product(newId, expirationDateValue));
                    } else {
                        productType.setAmount(productType.getAmount() + 1);
                        productViewModel.insert(new Product(productType.getProductTypeID(), expirationDateValue));
                        productTypeViewModel.update(productType);

                    }
                    popupWindow.dismiss();
                } else {
                    Toast.makeText(inflater.getContext(), "Puuttuva pvm tai nimi", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void insertDatesPopUp(View view, ViewGroup container, LayoutInflater inflater,
                                 FragmentActivity activity, LifecycleOwner owner, String typeName) {

        View popupView = inflater.inflate(R.layout.enter_expirationdate_popup_window, container, false);
        ProductTypeViewModel productTypeViewModel = new ViewModelProvider(activity).get(ProductTypeViewModel.class);
        ProductViewModel productViewModel = new ViewModelProvider(activity).get(ProductViewModel.class);

        Button add = popupView.findViewById(R.id.buttonAddProductPopUp);
        Button skip = popupView.findViewById(R.id.buttonSkipAddProductPopUp);
        editText = popupView.findViewById(R.id.inputExpirationDatePopUp);
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

        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                openDateDialog(inflater.getContext());
            }
        });

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().equals("")) {
                    List<ProductTypeWithProducts> productTypes = productTypeViewModel.getAllProductTypes().getValue();
                    boolean found = false;
                    ProductType productType = null;
                    int newId = 0;
                    for (int i = 0; i < productTypes.size(); i++) {
                        productType = productTypes.get(i).productType;
                        String name = productType.getProductTypeName();

                        if (name.equals(typeName)) {
                            found = true;
                            break;
                        }
                    }
                    //johki tähä
                    Date expirationDate = Date.valueOf(editText.getText().toString());
                    if (!found) {

                        newId = (int) productTypeViewModel.insert(new ProductType(typeName, 1));

                        List<ProductTypeWithProducts> updatedProductTypes = productTypeViewModel.getAllProductTypes().getValue();
                        productViewModel.insert(new Product(newId, expirationDate));
                    } else {
                        productType.setAmount(productType.getAmount() + 1);
                        productViewModel.insert(new Product(productType.getProductTypeID(), expirationDate));
                        productTypeViewModel.update(productType);

                    }
                    Toast.makeText(activity, "Tuote lisätty", Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                } else {
                    Toast.makeText(inflater.getContext(), "Puuttuva pvm", Toast.LENGTH_SHORT).show();
                }


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
