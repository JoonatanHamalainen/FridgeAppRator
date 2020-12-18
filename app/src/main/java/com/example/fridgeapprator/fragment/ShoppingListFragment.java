package com.example.fridgeapprator.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fridgeapprator.R;
import com.example.fridgeapprator.adapter.ShoppingListAdapter;
import com.example.fridgeapprator.model.ShoppingListProduct;
import com.example.fridgeapprator.model.ShoppingListWithShoppingListProducts;
import com.example.fridgeapprator.viewModel.ShoppingListProductViewModel;
import com.example.fridgeapprator.viewModel.ShoppingListViewModel;

import java.util.List;
import java.util.Objects;

public class ShoppingListFragment extends Fragment {

    RecyclerView recyclerView;
    ShoppingListAdapter adapter;
    private ShoppingListViewModel shoppingListViewModel;
    private ShoppingListProductViewModel shoppingListProductViewModel;
    private PopUpWindowController popUpWindowController;
    private EditText inputNewProductName, inputAmount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new ShoppingListAdapter(inflater.getContext());
        shoppingListViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(ShoppingListViewModel.class);
        popUpWindowController = new PopUpWindowController();
        shoppingListProductViewModel = new ViewModelProvider(getActivity()).get(ShoppingListProductViewModel.class);
        shoppingListViewModel.getAllShoppingListProducts().observe(getViewLifecycleOwner(),
                shoppingListProducts -> adapter.setShoppingListWithItsProducts(shoppingListProducts));

        View view = inflater.inflate(R.layout.shopping_list_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewShoppingList);
        this.inputNewProductName = view.findViewById(R.id.inputNewProductTypeName);
        this.inputAmount = view.findViewById(R.id.inputAmount);
        ImageButton addButton = view.findViewById(R.id.buttonplus);
        Button importShoppingList = view.findViewById(R.id.buttonImportProductsToFridge);


        addButton.setOnClickListener(view1 -> {

            //if neither one of the edittext fields is empty and amount given isn't under zero,
            //add a new product to the shopping list
            if (!inputNewProductName.getText().toString().equals("") && !inputAmount.getText().toString().equals("")) {
                if (Integer.parseInt(inputAmount.getText().toString()) > 0) {
                    List<ShoppingListProduct> shoppingListProducts = Objects.requireNonNull(shoppingListViewModel.getAllShoppingListProducts().getValue()).shoppingListProducts;
                    String typeName = inputNewProductName.getText().toString();
                    int amount = Integer.parseInt(inputAmount.getText().toString());
                    boolean found = false;
                    ShoppingListProduct shoppingListProduct = null;
                    for (int i = 0; i < shoppingListProducts.size(); i++) {
                        shoppingListProduct = shoppingListProducts.get(i);
                        String name = shoppingListProduct.getProductTypeName();

                        if (name.equals(typeName)) {
                            found = true;
                            break;
                        }
                    }

                    // if a product with the same name is not found in shopping list add it
                    if (!found) {
                        shoppingListProductViewModel.insert(new ShoppingListProduct(typeName, amount, 1));

                    }
                    //otherwise increase its amount
                    else {
                        shoppingListProduct.setAmount(shoppingListProduct.getAmount() + amount);
                        shoppingListProductViewModel.update(shoppingListProduct);
                    }
                    inputNewProductName.getText().clear();
                    inputAmount.setText("");
                }
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.addNewShoppinglistProductToast), Toast.LENGTH_SHORT).show();
            }
        });

        //creates as many popup windows as there are products in the shopping list.
        // In one popup window you're able to add one product to the fridge.
        importShoppingList.setOnClickListener(view12 -> {
            ShoppingListWithShoppingListProducts shoppingListWithShoppingListProducts = shoppingListViewModel.getAllShoppingListProducts().getValue();
            for (int i = 0; i < Objects.requireNonNull(shoppingListWithShoppingListProducts).shoppingListProducts.size(); i++) {
                for (int j = 0; j < shoppingListWithShoppingListProducts.shoppingListProducts.get(i).getAmount(); j++) {
                    popUpWindowController.insertDatesPopUp(view12, container, inflater, getActivity(), getViewLifecycleOwner(),
                            shoppingListWithShoppingListProducts.shoppingListProducts.get(i).getProductTypeName());

                }
            }
        });


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {


            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                shoppingListProductViewModel.delete(Objects.requireNonNull(shoppingListViewModel.getAllShoppingListProducts().getValue()).shoppingListProducts.get(position));

            }
        }));
        return view;
    }
}
