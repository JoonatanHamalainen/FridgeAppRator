package com.example.fridgeapprator.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fridgeapprator.R;

import com.example.fridgeapprator.adapter.FridgeProductListAdapter;

import com.example.fridgeapprator.adapter.ProductListAdapter;
import com.example.fridgeapprator.viewModel.ProductTypeViewModel;
import com.example.fridgeapprator.viewModel.ProductViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FridgeProductListFragment extends Fragment {

    RecyclerView fridgeRecyclerView, productRecyclerView;
    FridgeProductListAdapter fridgeProductListAdapter;
    ProductListAdapter productListAdapter;

    private ProductTypeViewModel productTypeViewModel;
    private ProductViewModel productViewModel;
    private OnItemTouchClickListener itemTouchListener;
    private FloatingActionButton addProductButton;
    PopUpWindowController popUpWindowController;
    //private onCurrencySelectListener mCallback;

    public interface OnItemTouchClickListener {
        void onItemClick(String name, double relation);
    }

    // the interface definition
    /*public interface onCurrencySelectListener {
        // called when a list item (Currency name) is selected
        public void onCurrencySelected(XMLCurrency xCurrency);
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
       /* try {
            mCallback = (onCurrencySelectListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnCurrencySelectedListener");
        }*/
    }

    OnBackPressedCallback callback = new OnBackPressedCallback(false) {
        @Override
        public void handleOnBackPressed() {

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fridgeProductListAdapter = new FridgeProductListAdapter(inflater.getContext());
        productListAdapter = new ProductListAdapter(inflater.getContext());
        popUpWindowController = new PopUpWindowController();
        productTypeViewModel = ViewModelProviders.of(this).get(ProductTypeViewModel.class);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productTypeViewModel.getAllProductTypes().observe(getViewLifecycleOwner(), productTypes -> {
            System.out.println("okei kävimme observis settaamas");
            fridgeProductListAdapter.setProductTypes(productTypes);
        });

        View view = inflater.inflate(R.layout.fridge_item_list_fragment, container, false);
        this.addProductButton = view.findViewById(R.id.floatingButtonAddProduct);
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
                //TÄNNE!
                if (productTypeViewModel.getAllProductTypes().getValue().get(position).productType.getAmount() < 1) {
                    productTypeViewModel.delete(productTypeViewModel.getAllProductTypes().getValue().get(position).productType);
                }
                //productTypeViewModel.delete(productTypeViewModel.getAllProductTypes().getValue().get(position).productType);

                //pProductTypeViewModel.delete(mCurrencyViewModel.getAllCurrencies().getValue().get(position));
            }
        }));

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popUpWindowController.insertSingleProductPopUp(view, container, inflater, getActivity(), getViewLifecycleOwner());
            }
        });

       /* recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View itemView = rv.findChildViewUnder(e.getX(), e.getY());
                if (itemView != null && itemTouchListener != null) {
                    itemTouchListener.onItemClick(adapter.getName(itemView), adapter.getRelation(itemView));

                }

                return false;
            }
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        }); */

        return view;
    }


    public void setOnItemTouchListener(OnItemTouchClickListener l) {
        itemTouchListener = l;
    }

}
