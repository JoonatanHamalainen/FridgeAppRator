package com.example.fridgeapprator.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fridgeapprator.R;
import com.example.fridgeapprator.adapter.ShoppingListAdapter;
import com.example.fridgeapprator.model.ShoppingListProduct;
import com.example.fridgeapprator.model.ShoppingListWithShoppingListProducts;
import com.example.fridgeapprator.viewModel.ShoppingListProductViewModel;
import com.example.fridgeapprator.viewModel.ShoppingListViewModel;

import java.util.List;

public class  ShoppingListFragment extends Fragment {

    RecyclerView recyclerView;
    ShoppingListAdapter adapter;
    private ShoppingListViewModel shoppingListViewModel;
    private ShoppingListProductViewModel shoppingListProductViewModel;
    private OnItemTouchClickListener itemTouchListener;
    private PopUpWindowController popUpWindowController;

    private EditText inputShoppingListName, inputNewProductName, inputAmount;
    private ImageButton addButton;
    private Button importShoppingList;

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
        popUpWindowController = new PopUpWindowController();
        shoppingListProductViewModel = ViewModelProviders.of(this).get(ShoppingListProductViewModel.class);
        shoppingListViewModel.getAllShoppingListProducts().observe(getViewLifecycleOwner(), shoppingListProducts -> {
            adapter.setShoppingListWithItsProducts(shoppingListProducts);
        });

        View view = inflater.inflate(R.layout.shopping_list_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewShoppingList);
        this.inputShoppingListName = view.findViewById(R.id.inputShoppingListName);
        this.inputNewProductName = view.findViewById(R.id.inputNewProductTypeName);
        this.inputAmount = view.findViewById(R.id.inputAmount);
        this.addButton = view.findViewById(R.id.buttonplus);
        this.importShoppingList = view.findViewById(R.id.buttonImportProductsToFridge);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!inputNewProductName.getText().toString().equals("") && Integer.parseInt(inputAmount.getText().toString()) > 0) {
                    List<ShoppingListProduct> shoppingListProducts = shoppingListViewModel.getAllShoppingListProducts().getValue().shoppingListProducts;
                    String typeName = inputNewProductName.getText().toString();
                    boolean found = false;
                    ShoppingListProduct shoppingListProduct = null;
                    int newId = 0;
                    for(int i = 0; i < shoppingListProducts.size(); i++) {
                        shoppingListProduct = shoppingListProducts.get(i);
                        String name = shoppingListProduct.getProductTypeName();

                        if(name.equals(typeName)) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        int amount = Integer.parseInt(inputAmount.getText().toString());
                        shoppingListProductViewModel.insert(new ShoppingListProduct(typeName, amount, 1));

                    }
                    else {
                        shoppingListProduct.setAmount(shoppingListProduct.getAmount() + 1);
                        shoppingListProductViewModel.update(shoppingListProduct);
                    }
                } else {
                    Toast.makeText(getActivity(), "Tekstikenttä tyhjä tai määrä on alle 1", Toast.LENGTH_SHORT).show();
                }
            }
        });

        importShoppingList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ShoppingListWithShoppingListProducts shoppingListWithShoppingListProducts = shoppingListViewModel.getAllShoppingListProducts().getValue();
                for (int i = 0; i < shoppingListWithShoppingListProducts.shoppingListProducts.size(); i++) {
                    for (int j = 0; j < shoppingListWithShoppingListProducts.shoppingListProducts.get(i).getAmount(); j++) {
                        popUpWindowController.insertDatesPopUp(view, container, inflater, getActivity(), getViewLifecycleOwner(),
                                shoppingListWithShoppingListProducts.shoppingListProducts.get(i).getProductTypeName());

                    }

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
                shoppingListProductViewModel.delete(shoppingListViewModel.getAllShoppingListProducts().getValue().shoppingListProducts.get(position));

                // mCurrencyViewModel.delete(mCurrencyViewModel.getAllCurrencies().getValue().get(position));

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
