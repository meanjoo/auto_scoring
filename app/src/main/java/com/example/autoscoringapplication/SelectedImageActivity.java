package com.example.autoscoringapplication;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autoscoringapplication.api.MyAPI;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectedImageActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private ImageView imageView;
    private Button btnOk;
    private File imageFile;

    private MyAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_image);

        api = ApiClient.getRetrofit().create(MyAPI.class);
        imageView = (ImageView) findViewById(R.id.image_view);
        btnOk = (Button) findViewById(R.id.btn_ok);

        String uri = getIntent().getExtras().getString("data");
        Log.d(TAG, uri);
        imageView.setImageURI(Uri.parse(uri));

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageFile = new File(uri2path(Uri.parse(uri)));
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
                // param 1: 전송될 때 key, param 2: 전송되는 파일 이름, param 3:
                MultipartBody.Part image = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);

                Log.d(TAG, "file name: " + imageFile.getName()); // 실제 파일 이름

                api.uploadImage(image).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "이미지 전송 성공");
                            Intent intent = new Intent(SelectedImageActivity.this, SelectAnswerActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.d(TAG, "Post image Status Code: " + response.code());
                            Log.d(TAG, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d(TAG, "onFailure");
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    private String uri2path(Uri uri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        cursor.moveToNext();
        String path = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
        cursor.close();

        return path;
    }
}
