package com.example.shoppingproject;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;

public class AdminEditCategory  extends AppCompatActivity {
    Button addCetgories;
    Button delCetgories;
    Button editCetgories;
    Button  pickImg;
    Button total;
    EditText categoryName;
    EditText delCategoryName;
    DBHelper db;
    ImageView categoryImg;

    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_edit_category);
        db=new DBHelper(this);
        categoryName=(EditText)findViewById(R.id.categoryName);
        delCategoryName=(EditText)findViewById(R.id.deleteCategoryName);
        addCetgories = findViewById(R.id.addBtn);
        categoryImg = findViewById(R.id.categoryImg);
        pickImg =findViewById(R.id.pickCategoryImg);
        total = findViewById(R.id.Totalcategory);
        delCetgories = findViewById(R.id.del);
        delCetgories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  name= delCategoryName.getText().toString();
                int c= db.deleteCategory(name);
                Toast.makeText(getApplicationContext(),c +"in delete",Toast.LENGTH_LONG).show();
            }
        });
        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = getCategoryCount();
                Toast.makeText(getApplicationContext(),c+"count",Toast.LENGTH_LONG).show();
            }
        });
        pickImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        addCetgories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  name= categoryName.getText().toString();
                if(name.equals("")){
                    Toast.makeText(getApplicationContext(),"enter a Category name ",Toast.LENGTH_SHORT).show();
                }
                else if(categoryImg.getDrawable() == null ){
                    Toast.makeText(getApplicationContext(),"pick a Category image ",Toast.LENGTH_SHORT).show();
                }
                else {
                    String msg = insertCategory(name);
                    Toast.makeText(getApplicationContext(),msg ,Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            categoryImg.setImageURI(imageUri);
        }
    }
    public String insertCategory(String name){
        db=new DBHelper(this);
        // get image from drawable
       // Bitmap image = BitmapFactory.decodeResource(getResources(),R.drawable.clothes);
        Bitmap image =((BitmapDrawable) categoryImg.getDrawable()).getBitmap();
        // convert bitmap to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte imageInByte[] = stream.toByteArray();
        String t =db.insertCategory(name ,imageInByte);
        return t;
    }
    public int getCategoryCount(){
        db=new DBHelper(this);
          int co = db.getCategoryCount();
        //Toast.makeText(getApplicationContext(),co ,Toast.LENGTH_SHORT).show();

        return co;
    }
}
