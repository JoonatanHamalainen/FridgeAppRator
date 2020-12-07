package com.example.fridgeapprator.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fridgeapprator.R;
import com.example.fridgeapprator.model.ProductTypeWithProducts;
import com.example.fridgeapprator.model.ShoppingListWithShoppingListProducts;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {


    class ShoppingListViewHolder extends RecyclerView.ViewHolder {
        private final EditText inputShoppingListName;
        private final EditText inputNewProductName;
        private final EditText inputAmount;

        private final TextView shoppingListProductTypeName;
        private final TextView shoppingListProductTypeAmount;

        public ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.inputShoppingListName = itemView.findViewById(R.id.inputShoppingListName);
            this.inputNewProductName = itemView.findViewById(R.id.inputNewProductTypeName);
            this.inputAmount = itemView.findViewById(R.id.inputAmount);
            this.shoppingListProductTypeName = itemView.findViewById(R.id.productTypeName);
            this.shoppingListProductTypeAmount = itemView.findViewById(R.id.productTypeAmount);

        }
    }

    private final LayoutInflater inflater;
    private ShoppingListWithShoppingListProducts shoppingListWithItsProducts = null;

    ShoppingListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_fridge_item, parent, false);
        return new ShoppingListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {

        if (shoppingListWithItsProducts != null) {
            ShoppingListWithShoppingListProducts current = shoppingListWithItsProducts;
            holder.shoppingListProductTypeName.setText(current.shoppingListProducts.get(position).getProductTypeName());
            holder.shoppingListProductTypeAmount.setText(Integer.toString(current.shoppingListProducts.get(position).getAmount()));
        } else {
            holder.shoppingListProductTypeName.setText(R.string.noProductsFound);
        }

    }

    void setShoppingListWithItsProducts(ShoppingListWithShoppingListProducts shoppingListWithItsProducts){
        this.shoppingListWithItsProducts = shoppingListWithItsProducts;
        Collections.reverse(this.shoppingListWithItsProducts.shoppingListProducts);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(shoppingListWithItsProducts != null)
            return shoppingListWithItsProducts.shoppingListProducts.size();
        return 0;
    }


}
