package com.example.fridgeapprator.fragment;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fridgeapprator.R;

public class WelcomePageFragment extends Fragment {

    public WelcomePageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.start_fragment, container, false);
        return v;
    }
}
