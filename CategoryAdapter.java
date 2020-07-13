package com.example.shoppingproject;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Category> data = new ArrayList<Category>();
    private LayoutInflater inflater;
    Context context;

    public CategoryAdapter(Context context, List<Category> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.category_lists, parent,false);
        CategoryHolder holder=new CategoryHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Get current position of item in recyclerview to bind data and assign values from list
        final CategoryHolder myHolder= (CategoryHolder) holder;
        final Category category=data.get(position);
        myHolder.txtTitle.setText(category.getcategoryName());

        //convert byte to bitmap take from Category class
        byte[] outImage=category.getCategoryImg();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        myHolder.imgIcon.setImageBitmap(theImage);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        // init the item view's
        TextView txtTitle;
        ImageView imgIcon;
        public CategoryHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            txtTitle= (TextView) itemView.findViewById(R.id.txtTitle);
            imgIcon= (ImageView) itemView.findViewById(R.id.imgIcon);
            imgIcon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent categoryIntent = new Intent(context, CategoryDetails.class);
            categoryIntent.putExtra("categoryName",txtTitle.getText().toString());
            context.startActivity(categoryIntent);
        }
    }
}