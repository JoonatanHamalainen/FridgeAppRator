package com.example.fridgeapprator.fragment;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fridgeapprator.R;

public class InstructionsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.instructions_fragment, container, false);

        TextView fridgeInstructionsLink = (TextView) view.findViewById(R.id.instructions_main_header_fridge);
        TextView shoppingListInstructionsLink = (TextView) view.findViewById(R.id.instructions_main_header_shopping_list);
        TextView notificationsInstructionsLink = (TextView) view.findViewById(R.id.instructions_main_header_notifications);
        fridgeInstructionsLink.setMovementMethod(LinkMovementMethod.getInstance());
        shoppingListInstructionsLink.setMovementMethod(LinkMovementMethod.getInstance());
        notificationsInstructionsLink.setMovementMethod(LinkMovementMethod.getInstance());

        fridgeInstructionsLink.setOnClickListener(instructionsView -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            InstructionsFridgeFragment instructionsFridgeFragment = new InstructionsFridgeFragment();
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment_container, instructionsFridgeFragment)
                    .commit();
        });

        shoppingListInstructionsLink.setOnClickListener(instructionsView -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            InstructionsShoppingListFragment instructionsShoppingListFragment = new InstructionsShoppingListFragment();
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment_container, instructionsShoppingListFragment)
                    .commit();
        });

        notificationsInstructionsLink.setOnClickListener(instructionsView -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            InstructionsNotificationsFragment instructionsNotificationsFragment = new InstructionsNotificationsFragment();
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment_container, instructionsNotificationsFragment)
                    .commit();
        });

        return view;

    }

    public static class InstructionsFridgeFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            return inflater.inflate(R.layout.instructions_fridge_fragment, container, false);
        }
    }

    public static class InstructionsShoppingListFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            return inflater.inflate(R.layout.instructions_shopping_list_fragment, container, false);
        }
    }

    public static class InstructionsNotificationsFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            return inflater.inflate(R.layout.instructions_notifications_fragment, container, false);
        }
    }

}
