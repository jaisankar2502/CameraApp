package com.surya.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class ShowphotoActivity extends AppCompatActivity {
    ImageView showimageView;
    Button button;
    String path="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showphoto);
        showimageView= findViewById(R.id.imageview2);
        button=findViewById(R.id.btnedit);
        path=getIntent().getExtras().getString("path");
        File imagefile= new File(path);
        if (imagefile.exists()){
            Bitmap mybitmap= BitmapFactory.decodeFile(imagefile.getAbsolutePath());
            showimageView.setImageBitmap(RotateBitmap(mybitmap,90));

        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getApplicationContext(),EditphotoActivity.class);
                i.putExtra("path",path);
                startActivity(i);

            }
        });



    }
    public  static Bitmap RotateBitmap(Bitmap source,float angle){
        Matrix matrix= new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source,0,0,source.getWidth(),source.getHeight(),matrix,true);

    }
}