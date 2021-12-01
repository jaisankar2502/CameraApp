package com.surya.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView imageView,gallery;

    private  int REQUEST_CODE_PERMISSION=101;
    public static  final   int PICK_IMAGE_REQUEST=102;
    private  final String[] REQUEST_PERMISSION= new String[]{
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView= findViewById(R.id.cameraicon);
        gallery= findViewById(R.id.Gallery);

        if (allPermisionsGranded()){

        }
        else
        {
            ActivityCompat.requestPermissions(this,REQUEST_PERMISSION,REQUEST_CODE_PERMISSION);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // connect to next page
                startActivity(new Intent(getApplicationContext(),CameraActivity.class));
            }
        });
gallery.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i= new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i,"selected image"),PICK_IMAGE_REQUEST);


    }
});
    }
    public String getrealpath(Context context, Uri contenturi){
        Cursor cursor= getContentResolver().query(contenturi,null,null,null,null);
        cursor.moveToFirst();
        String document_id=cursor.getString(0);
        document_id=document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor=getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,MediaStore.Images.Media._ID+" = ?",new String[]{document_id},null);
   cursor.moveToFirst();
   @SuppressLint("Range") String path= cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
    cursor.close();
    return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_IMAGE_REQUEST:
                if (resultCode==RESULT_OK){
                    try {
                        Uri uri= data.getData();
                        String path= getrealpath(getApplicationContext(),uri);
                        Toast.makeText(getApplicationContext(), path+"", Toast.LENGTH_SHORT).show();
                        Intent i= new Intent(getApplicationContext(),ShowphotoActivity.class);
                        i.putExtra("path",path+"");
                        startActivity(i);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

        }
    }

    private boolean allPermisionsGranded() {
        for (String permission : REQUEST_PERMISSION) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
             return  false;
            }
        }
        return true;
    }
}