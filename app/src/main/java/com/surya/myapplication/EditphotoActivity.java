package com.surya.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.utils.ViewSpline;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class EditphotoActivity extends AppCompatActivity {
    Button save, crop, rotate, undo;
    ImageView editimage;
    String imgpath = "";
    Bitmap bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editphoto);
        save = findViewById(R.id.save);
        crop = findViewById(R.id.Crop);
        rotate = findViewById(R.id.Rotate);
        editimage = findViewById(R.id.imageview3);
        undo = findViewById(R.id.undo);
        imgpath = getIntent().getExtras().getString("path");
        File imagefile = new File(imgpath);
        if (imagefile.exists()) {
            bitmap = BitmapFactory.decodeFile(imagefile.getAbsolutePath());
            editimage.setImageBitmap(RotateBitmap(bitmap, 90));
        }
        final int[] angle = {0};
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                angle[0] = angle[0] + 90;
                editimage.setRotation(angle[0]);

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                try {
                    save(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int angl=0;
                editimage.setRotation(angl);

            }
        });

    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

    }

    public void rotate(View view) {


    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void save(View view) throws IOException {


        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis() + ".jpg");
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

        ContentResolver resolver = getContentResolver();
        Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        OutputStream imageOutStream = null;

        try {
            if (uri == null) {
                throw new IOException("Failed to insert MediaStore row");
            }

            imageOutStream = resolver.openOutputStream(uri);
            if (bitmap != null) {
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageOutStream)) {
                    throw new IOException("Failed to compress bitmap");
                }
            } else if (bitmap != null) {
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageOutStream)) {
                    throw new IOException("Failed to compress bitmap");
                }
            } else if (bitmap != null) {
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageOutStream)) {
                    throw new IOException("Failed to compress bitmap");
                }
            } else if (bitmap != null) {
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageOutStream)) {
                    throw new IOException("Failed to compress bitmap");
                }
            } else {
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageOutStream)) {
                    throw new IOException("Failed to compress bitmap");
                }
            }

            Toast.makeText(this, "Imave Saved", Toast.LENGTH_SHORT).show();

        } finally {
            if (imageOutStream != null) {
                imageOutStream.close();
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                finish();
                startActivity(intent);
            }
        }

    }
//    public void undo(View view) {
//        Matrix matrix = new Matrix();
//
//
//        toRotation = mCurrRotation += 90;
//
//        final RotateAnimation rotateAnimation = new RotateAnimation(
//                fromRotation, 0, imageView.getWidth() / 2, imageView.getHeight() / 2);
//
//        rotateAnimation.setDuration(1000);
//        rotateAnimation.setFillAfter(true);
//
//
//        matrix.equals(toRotation);
//        System.out.println(toRotation + "TO ROTATION");
//        System.out.println(fromRotation + "FROM ROTATION");
//        Bitmap bitmap = null;
////        if (croppedBitmap != null) {
////            cropThenRotateBitmap = Bitmap.createBitmap(croppedBitmap, 0, 0, croppedBitmap.getWidth(), croppedBitmap.getHeight(), matrix, true);
////        } else {
////            rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
////        }
////
//        imageView.setImageBitmap(bitmap);
//        imageView.startAnimation(rotateAnimation);
//        makeBitmapNull();
//    }
//
//
//    public void makeBitmapNull(){
//        mCurrRotation=0;
//        toRotation=0;
//        fromRotation=0;
//        rotateBitmap=null;
//        croppedBitmap=null;
//        rotateThenCropBitmap=null;
//        cropThenRotateBitmap=null;
//    }


}