package com.example.fridgeapprator.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fridgeapprator.R;
import com.example.fridgeapprator.model.ShoppingListProduct;
import com.example.fridgeapprator.viewModel.ProductTypeViewModel;
import com.example.fridgeapprator.viewModel.ShoppingListProductViewModel;
import com.example.fridgeapprator.viewModel.ShoppingListViewModel;

public class  ShoppingListFragment extends Fragment {

    RecyclerView recyclerView;
    ShoppingListAdapter adapter;
    private ShoppingListViewModel shoppingListViewModel;
    private ShoppingListProductViewModel shoppingListProductViewModel;
    private OnItemTouchClickListener itemTouchListener;

    private EditText inputShoppingListName, inputNewProductName, inputAmount;
    private ImageButton addButton;

    public interface OnItemTouchClickListener {
        void onItemClick(String name, double relation);
    }

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
        adapter = new ShoppingListAdapter(inflater.getContext());
        shoppingListViewModel = ViewModelProviders.of(this).get(ShoppingListViewModel.class);
        shoppingListProductViewModel = ViewModelProviders.of(this).get(ShoppingListProductViewModel.class);
        shoppingListViewModel.getShoppingListWithItsProducts(1).observe(getViewLifecycleOwner(), shoppingListProducts -> {
            adapter.setShoppingListWithItsProducts(shoppingListProducts);
        });

        View view = inflater.inflate(R.layout.shopping_list_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewShoppingList);
        this.inputShoppingListName = view.findViewById(R.id.inputShoppingListName);
        this.inputNewProductName = view.findViewById(R.id.inputNewProductTypeName);
        this.inputAmount = view.findViewById(R.id.inputAmount);
        this.addButton = view.findViewById(R.id.buttonplus);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!inputNewProductName.getText().toString().equals("") && Integer.parseInt(inputAmount.getText().toString()) > 0) {
                    String name = inputNewProductName.getText().toString();
                    int amount = Integer.parseInt(inputAmount.getText().toString());
                    shoppingListProductViewModel.insert(new ShoppingListProduct(name, amount, 1));

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

                //Toast.makeText(getActivity(), position+ " is selected successfully", Toast.LENGTH_SHORT).show();

                //handle click event
                // String productTypeName  = adapter.getName(view); // get currency info from adapter
                // int productTypeAmountI =  adapter.getAmount(view);
                // String productTypeAmountS = Integer.toString(productTypeAmountI);
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
}
