package com.example.fridgeapprator.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fridgeapprator.R;
import com.example.fridgeapprator.model.ProductTypeWithProducts;

import java.util.List;

public class FridgeProductListAdapter extends RecyclerView.Adapter<FridgeProductListAdapter.FridgeProductViewHolder> {

    class FridgeProductViewHolder extends RecyclerView.ViewHolder {

        private final TextView fridgeProductTypeName;
        private final TextView fridgeProductTypeAmount;

        private FridgeProductViewHolder(@NonNull View itemView) {
            super(itemView);

            this.fridgeProductTypeName = itemView.findViewById(R.id.productTypeName);
            this.fridgeProductTypeAmount = itemView.findViewById(R.id.productTypeAmount);
        }
    }

    private final LayoutInflater inflater;
    private List<ProductTypeWithProducts> productTypes = null;

    FridgeProductListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public FridgeProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_fridge_item, parent, false);
        return new FridgeProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FridgeProductViewHolder holder, int position) {
        if (productTypes != null) {
            ProductTypeWithProducts current = productTypes.get(position);
            holder.fridgeProductTypeName.setText(current.productType.getProductTypeName());
            holder.fridgeProductTypeAmount.setText(Integer.toString(current.productType.getAmount()));
        } else {
            // Covers the case of data not being ready yet.
            holder.fridgeProductTypeName.setText(R.string.noProductsFound);
        }
    }

    public String getName(View itemView) {
        return ((TextView) itemView.findViewById(R.id.productTypeName)).getText().toString();
    }

    public int getAmount(View itemView) {
        return Integer.parseInt(
                ((TextView) itemView.findViewById(R.id.productTypeAmount))
                        .getText().toString()
        );
    }

    void setProductTypes(List<ProductTypeWithProducts> productTypes){
        this.productTypes = productTypes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (productTypes != null)
            return productTypes.size();
        else return 0;
    }
}
