package com.example.autoscoringapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class UploadActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private ImageView btnCamera, btnImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        btnCamera = (ImageView) findViewById(R.id.btn_camera);
        btnImage = (ImageView) findViewById(R.id.btn_image);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 카메라로 촬영 가능하도록
                Log.d(TAG, "카메라로 사진 촬영 버튼");
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 갤러리 진입 가능하도록 (권한 및 그런 거 해야할 듯)
                Log.d(TAG, "이미지 파일 업로드 버튼");
            }
        });
    }
}
