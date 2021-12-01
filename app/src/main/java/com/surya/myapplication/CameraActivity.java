package com.surya.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.lifecycle.LifecycleOwner;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class    CameraActivity extends AppCompatActivity {
    TextureView textureView;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        textureView = findViewById(R.id.viewfinder);
        imageView2 = findViewById(R.id.imageCapture);
        startcamera();

    }
    public void startcamera(){
        CameraX.unbindAll();
        Rational aspectRatio= new Rational(textureView.getWidth(),textureView.getHeight());
        Size screen= new Size(textureView.getWidth(),textureView.getHeight());

        PreviewConfig previewConfig= new  PreviewConfig.Builder().setTargetAspectRatio(aspectRatio).setTargetResolution(screen).build();
        Preview preview= new Preview(previewConfig);
        preview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
            @Override
            public void onUpdated(Preview.PreviewOutput output) {
                ViewGroup parent= (ViewGroup) textureView.getParent();
                parent.removeView(textureView);
                parent.addView(textureView,0);
                textureView.setSurfaceTexture(output.getSurfaceTexture());
                updateTransform();

            }
        });
        ImageCaptureConfig imageCaptureConfig= new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY).setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();

        final ImageCapture imgcap=new ImageCapture(imageCaptureConfig);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File  file= new File(Environment.getExternalStorageDirectory()+"/"+System.currentTimeMillis()+".jpg");
                imgcap.takePicture(file, new ImageCapture.OnImageSavedListener() {
                    // android 9 ,10
                    @Override
                    public void onImageSaved(@NonNull File file) {
                         String msg="Picture Capture at"+file.getAbsolutePath();
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(CameraActivity.this,ShowphotoActivity.class);
                        intent.putExtra("path",file.getAbsolutePath()+"");
                        startActivity(intent);
                    }

                    @Override
                    public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {
                        String msg="Picture Capture Faild"+message;
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        if (cause!=null){
                            cause.printStackTrace();
                        }


                    }
                });
            }
        });
        CameraX.bindToLifecycle((LifecycleOwner)this,preview,imgcap);

    }

    public void updateTransform() {
        Matrix mx= new Matrix();
        float w = textureView.getMeasuredWidth();
        float h = textureView.getMeasuredHeight();
        float cx = w / 2f;
        float cy = h / 2f;
        int rotationDrg = 90;
        int rotation = (int) textureView.getRotation();
        switch (rotation) {
            case Surface
                    .ROTATION_0:
                rotationDrg = 0;
                break;
            case Surface.ROTATION_90:
                rotationDrg = 90;
                break;
            case Surface.ROTATION_180:
                rotationDrg = 180;
                break;
            case Surface.ROTATION_270:
                rotationDrg = 270;
                break;
            default:
                return;
        }
         // ======rotation degree and point to rotate====
         mx.postRotate( (float) rotationDrg,cx,cy);
        textureView.setTransform(mx);


    }

}