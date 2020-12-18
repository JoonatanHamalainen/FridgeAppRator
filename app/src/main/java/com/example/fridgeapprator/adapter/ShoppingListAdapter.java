package com.example.fridgeapprator.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fridgeapprator.R;
import com.example.fridgeapprator.model.ShoppingListWithShoppingListProducts;

import java.util.Collections;


public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {


    private final LayoutInflater inflater;
    private ShoppingListWithShoppingListProducts shoppingListWithItsProducts = null;

    public ShoppingListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_fridge_item, parent, false);
        return new ShoppingListViewHolder(itemView);
    }

    private void checkItem(ShoppingListViewHolder holder) {
        boolean value = holder.shoppingListProductTypeName.isChecked();

        if (value) {

            holder.shoppingListProductTypeName.setCheckMarkDrawable(R.drawable.check_ic);
            holder.shoppingListProductTypeName.setChecked(false);
        } else {

            holder.shoppingListProductTypeName.setCheckMarkDrawable(R.drawable.check);
            holder.shoppingListProductTypeName.setChecked(true);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {

        holder.shoppingListProductTypeName.setOnClickListener(view -> checkItem(holder));

        holder.shoppingListProductTypeAmount.setOnClickListener(view -> checkItem(holder));

        ShoppingListWithShoppingListProducts current = shoppingListWithItsProducts;
        holder.shoppingListProductTypeName.setText(current.shoppingListProducts.get(position).getProductTypeName());
        holder.shoppingListProductTypeAmount.setText(Integer.toString(current.shoppingListProducts.get(position).getAmount()));

    }

    public void setShoppingListWithItsProducts(ShoppingListWithShoppingListProducts shoppingListWithItsProducts) {
        this.shoppingListWithItsProducts = shoppingListWithItsProducts;
        Collections.reverse(this.shoppingListWithItsProducts.shoppingListProducts);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (shoppingListWithItsProducts != null)
            return shoppingListWithItsProducts.shoppingListProducts.size();
        return 0;
    }

    static class ShoppingListViewHolder extends RecyclerView.ViewHolder {


        private final CheckedTextView shoppingListProductTypeName;
        private final TextView shoppingListProductTypeAmount;

        public ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.shoppingListProductTypeName = itemView.findViewById(R.id.productTypeName);
            this.shoppingListProductTypeAmount = itemView.findViewById(R.id.productTypeAmount);

        }
    }


}
