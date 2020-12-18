package com.example.fridgeapprator;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.fridgeapprator.fragment.FridgeProductListFragment;
import com.example.fridgeapprator.fragment.InstructionsFragment;
import com.example.fridgeapprator.fragment.ShoppingListFragment;
import com.example.fridgeapprator.fragment.WelcomePageFragment;
import com.example.fridgeapprator.viewModel.ShoppingListViewModel;

public class MainActivity extends AppCompatActivity {

    ShoppingListViewModel shoppingListViewModel;
    WelcomePageFragment welcomePageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shoppingListViewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            welcomePageFragment = new WelcomePageFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, welcomePageFragment, "welcomePageFragment");
            transaction.commit();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_shoppingList) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment_container, shoppingListFragment)
                    .commit();
            return true;
        } else if (id == R.id.menu_fridge) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            FridgeProductListFragment fridgeProductListFragment = new FridgeProductListFragment();
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment_container, fridgeProductListFragment)
                    .commit();
            return true;
        } else if (id == R.id.menu_instructions) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            InstructionsFragment instructionsFragment = new InstructionsFragment();
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment_container, instructionsFragment)
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}