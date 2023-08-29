package com.flaxeninfosoft.guptaoffset.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.DistrictNameLayoutBinding;
import com.flaxeninfosoft.guptaoffset.databinding.ProductLayoutBinding;
import com.flaxeninfosoft.guptaoffset.models.DistrictData;
import com.flaxeninfosoft.guptaoffset.models.Products;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    List<Products> productsList;
    ProductClickListener productClickListener;

    public ProductsAdapter(List<Products> productsList, ProductClickListener productClickListener) {
        this.productsList = productsList;
        this.productClickListener = productClickListener;
    }

    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.product_layout,parent,false);
        return new ViewHolder(binding,productClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder holder, int position) {
        holder.setData(productsList.get(position));
    }

    @Override
    public int getItemCount() {
        if (productsList == null) {
            return 0;
        } else {
            return productsList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ProductLayoutBinding binding;
        ProductClickListener productClickListener;
        public ViewHolder(@NonNull ProductLayoutBinding binding, ProductClickListener productClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.productClickListener = productClickListener;
        }
        public void setData(Products products) {
            binding.setProduct(products);
            binding.getRoot().setOnClickListener(view -> productClickListener.onClickProduct(products));

        }

    }


    public interface ProductClickListener{
        void onClickProduct(Products products);
    }
}
