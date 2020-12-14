package com.example.fridgeapprator.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fridgeapprator.R;

public class WelcomePageFragment extends Fragment {

    public WelcomePageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.welcome_page_fragment, container, false);
        Button toNewShoppingList = v.findViewById(R.id.button_to_new_shopping_list);
        Button toAddProduct = v.findViewById(R.id.button_to_add_product);
        Button toInstructions = v.findViewById(R.id.button_to_instructions);

        toAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                //transaction.remove(fragment).commit(); // wipe the old
                FridgeProductListFragment fridgeProductListFragment = new FridgeProductListFragment();
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragment_container, fridgeProductListFragment)
                        .commit();
            }
        });

        toNewShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                //transaction.remove(fragment).commit(); // wipe the old
                ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragment_container, shoppingListFragment)
                        .commit();
            }
        });

        toInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                //transaction.remove(fragment).commit(); // wipe the old
                InstructionsFragment instructionsFragment = new InstructionsFragment();
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragment_container, instructionsFragment)
                        .commit();
            }
        });
        return v;
    }
}
