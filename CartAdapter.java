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

public class CartAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CartProducts> data = new ArrayList<CartProducts>();
    private LayoutInflater inflater;
    Context context;

    public CartAdapter(Context context, List<CartProducts> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.cart_products, parent,false);
        CartAdapter.CartHolder holder=new CartAdapter.CartHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final CartAdapter.CartHolder myHolder= (CartAdapter.CartHolder) holder;
        final CartProducts cartproduct=data.get(position);
        myHolder.txtTitle.setText(cartproduct.getproductName());
        myHolder.price.setText(cartproduct.getPrice());
        myHolder.quantity.setText(cartproduct.getqty());
        //convert byte to bitmap
        byte[] outImage=cartproduct.getproductImg();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        myHolder.imgIcon.setImageBitmap(theImage);
    }

    @Override
    public int getItemCount(){
            return data.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // init the item view's
        TextView txtTitle;
        TextView  price;
        TextView quantity;
        ImageView imgIcon;
        public CartHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            txtTitle= (TextView) itemView.findViewById(R.id.proName);
            imgIcon= (ImageView) itemView.findViewById(R.id.proImg);
            price= (TextView) itemView.findViewById(R.id.price);
            quantity= (TextView) itemView.findViewById(R.id.qty);
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
