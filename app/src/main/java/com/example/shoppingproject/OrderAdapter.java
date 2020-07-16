package com.example.shoppingproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class OrderAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OrderedProducts> data = new ArrayList<OrderedProducts>();
    private LayoutInflater inflater;
    Context context;
    DBHelper db;

    public OrderAdapter(Context context, List<OrderedProducts> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.cart_products, parent,false);
        OrderAdapter.OrderHolder holder=new OrderAdapter.OrderHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final OrderAdapter.OrderHolder myHolder= (OrderAdapter.OrderHolder) holder;
        final OrderedProducts orderedProduct=data.get(position);
        myHolder.txtTitle.setText(orderedProduct.getproductName());
        myHolder.price.setText(""+orderedProduct.getPrice());
        myHolder.quantity.setText(""+orderedProduct.getqty());
        //myHolder.OrderID.setText(""+orderedProduct.getOrderID());
        //convert byte to bitmap
        byte[] outImage=orderedProduct.getproductImg();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        myHolder.imgIcon.setImageBitmap(theImage);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class OrderHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView txtTitle;
        TextView  price;
        TextView quantity;
        ImageView imgIcon;
        TextView OrderID;
        public OrderHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            txtTitle= (TextView) itemView.findViewById(R.id.proName);
            imgIcon= (ImageView) itemView.findViewById(R.id.proImg);
            price= (TextView) itemView.findViewById(R.id.price);
            quantity= (TextView) itemView.findViewById(R.id.qty);
            OrderID= (TextView) itemView.findViewById(R.id.OrderID);

        }

    }
}
