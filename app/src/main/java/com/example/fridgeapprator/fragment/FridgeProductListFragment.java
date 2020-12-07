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
import com.example.fridgeapprator.model.ProductType;
import com.example.fridgeapprator.viewModel.ProductTypeViewModel;

public class FridgeProductListFragment extends Fragment {

    ProductTypeViewModel productTypeViewModel;
    RecyclerView recyclerView;
    FridgeProductListAdapter adapter;
    private ProductTypeViewModel pProductTypeViewModel;
    private OnItemTouchClickListener itemTouchListener;
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
        adapter = new FridgeProductListAdapter(inflater.getContext());
        pProductTypeViewModel = ViewModelProviders.of(this).get(ProductTypeViewModel.class);
        pProductTypeViewModel.getAllProductTypes().observe(getViewLifecycleOwner(), productTypes -> {
            adapter.setProductTypes(productTypes);
        });

        View view = inflater.inflate(R.layout.fridge_item_list_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                //Toast.makeText(getActivity(), position+ " is selected successfully", Toast.LENGTH_SHORT).show();

                //handle click event
                String productTypeName  = adapter.getName(view); // get currency info from adapter
                int productTypeAmountI =  adapter.getAmount(view);
                String productTypeAmountS = Integer.toString(productTypeAmountI);
                /*XMLCurrency curr = new XMLCurrency();
                curr.setName(currencyName);
                curr.setRelation(currencyRelation);
                mCallback.onCurrencySelected(curr);*/
            }

            @Override
            public void onLongClick(View view, int position) {

                //pProductTypeViewModel.delete(mCurrencyViewModel.getAllCurrencies().getValue().get(position));
            }
        }));
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
