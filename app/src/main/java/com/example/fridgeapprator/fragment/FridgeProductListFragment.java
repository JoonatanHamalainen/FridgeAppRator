package com.example.fridgeapprator.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fridgeapprator.R;

import com.example.fridgeapprator.adapter.FridgeProductListAdapter;

import com.example.fridgeapprator.adapter.ProductListAdapter;
import com.example.fridgeapprator.viewModel.ProductTypeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class FridgeProductListFragment extends Fragment {

    RecyclerView fridgeRecyclerView;
    FridgeProductListAdapter fridgeProductListAdapter;
    ProductListAdapter productListAdapter;

    private ProductTypeViewModel productTypeViewModel;
    PopUpWindowController popUpWindowController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fridgeProductListAdapter = new FridgeProductListAdapter(inflater.getContext());
        productListAdapter = new ProductListAdapter(inflater.getContext());
        popUpWindowController = new PopUpWindowController();
        productTypeViewModel =  new ViewModelProvider(Objects.requireNonNull(getActivity())).get(ProductTypeViewModel.class);
        productTypeViewModel.getAllProductTypes().observe(getViewLifecycleOwner(), productTypes -> fridgeProductListAdapter.setProductTypes(productTypes));

        View view = inflater.inflate(R.layout.fridge_item_list_fragment, container, false);
        FloatingActionButton addProductButton = view.findViewById(R.id.floatingButtonAddProduct);
        fridgeRecyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        fridgeRecyclerView.setLayoutManager(llm);
        fridgeRecyclerView.setAdapter(fridgeProductListAdapter);
        fridgeRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                fridgeRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                popUpWindowController.showProductTypeProductsPopUp(view, container, position, inflater, getActivity(), getViewLifecycleOwner());
            }

            @Override
            public void onLongClick(View view, int position) {
                if (Objects.requireNonNull(productTypeViewModel.getAllProductTypes().getValue()).get(position).productType.getAmount() < 1) {
                    productTypeViewModel.delete(productTypeViewModel.getAllProductTypes().getValue().get(position).productType);
                }
            }
        }));

        addProductButton.setOnClickListener(view1 -> popUpWindowController.insertSingleProductPopUp(view1, container, inflater, getActivity(), getViewLifecycleOwner()));


        return view;
    }



}
