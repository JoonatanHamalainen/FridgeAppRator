package com.example.fridgeapprator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fridgeapprator.R;
import com.example.fridgeapprator.model.Product;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        private final TextView productExpirationDate;

        private ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            this.productExpirationDate = itemView.findViewById(R.id.productExpirationDate);
        }
    }

    private final LayoutInflater inflater;
    private List<Product> products = null;

     public ProductListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_product_item, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

         if (products != null) {
            Product current = products.get(position);
            holder.productExpirationDate.setText(current.getExpirationDate().toString());
         }

    }

    public void setProducts(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (products != null)
            return products.size();
        else return 0;
    }

}
