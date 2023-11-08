package com.example.autoscoringapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.graphics.Bitmap;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class UploadActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 102;

    private static final int GALLERY_PERMISSION_REQUEST_CODE = 101;
    private boolean isGalleryPermissionGranted = false;

    private final String TAG = this.getClass().getSimpleName();
    private ImageView btnCamera, btnImage;
    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Log.d(TAG, result.getData().toString());
                        Log.d(TAG, result.getData().getData().toString());
                        Intent intent = new Intent(getApplicationContext(), SelectedImageActivity.class);

                        intent.putExtra("data", result.getData().getData().toString());
                        startActivity(intent);
                    }
                }
            }
    );
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(cameraIntent);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivity(galleryIntent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        btnCamera = (ImageView) findViewById(R.id.btn_camera);
        btnImage = (ImageView) findViewById(R.id.btn_image);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(UploadActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(
                            UploadActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            CAMERA_PERMISSION_REQUEST_CODE
                    );
                } else {
                    openCamera();
                }
            }
        });

//        btnCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 카메라로 촬영 가능하도록 https://developer.android.com/guide/components/intents-common?hl=ko
//                Log.d(TAG, "카메라로 사진 촬영 버튼");
//                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                //launcher.launch(intent);
//            }
//        });

        btnImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 갤러리 진입 가능하도록
                Log.d(TAG, "이미지 파일 업로드 버튼");
                // 권한 획득?
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                }

                // https://cishome.tistory.com/243
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_PICK);
                launcher.launch(Intent.createChooser(galleryIntent, "작업을 수행할 때 사용할 애플리케이션"));

                // https://velog.io/@ahneve/AndroidJava-%EA%B0%A4%EB%9F%AC%EB%A6%AC%EC%97%90%EC%84%9C-%EC%82%AC%EC%A7%84-%EB%B6%88%EB%9F%AC%EC%98%A4%EA%B8%B0-deprecated%EB%90%9C-startActivityForResult-onActivityResult-%ED%95%A8%EC%88%98
            }
        });
    }
}
