package com.example.fridgeapprator.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.fridgeapprator.R;

public class  ShoppingListFragment extends Fragment {

    public ShoppingListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shopping_list_fragment, container, false);
        return v;
    }
}
