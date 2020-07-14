package com.example.shoppingproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<Product> data = new ArrayList<Product>();
    private LayoutInflater inflater;
    Context context;

    public ProductAdapter(Context context, List<Product> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.product_lists, parent,false);
        ProductAdapter.ProductHolder holder=new ProductAdapter.ProductHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
// Get current position of item in recyclerview to bind data and assign values from list
        final ProductAdapter.ProductHolder myHolder= (ProductAdapter.ProductHolder) holder;
        final Product product=data.get(position);
        myHolder.txtTitle.setText(product.getproductName());

        //convert byte to bitmap
        byte[] outImage=product.getproductImg();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        myHolder.imgIcon.setImageBitmap(theImage);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // init the item view's
        TextView txtTitle;
        ImageView imgIcon;
        public ProductHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            txtTitle= (TextView) itemView.findViewById(R.id.producttxtTitle);
            imgIcon= (ImageView) itemView.findViewById(R.id.productImgIcon);
            imgIcon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent productIntent = new Intent(context, ProductDetails.class);
            productIntent.putExtra("ProductName",txtTitle.getText().toString());
            context.startActivity(productIntent);
        }
    }
}
