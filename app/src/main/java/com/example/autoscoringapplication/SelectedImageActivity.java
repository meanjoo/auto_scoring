package com.example.autoscoringapplication;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectedImageActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_image);

        imageView = (ImageView) findViewById(R.id.image_view);

        String uri = getIntent().getExtras().getString("data");
        Log.d(TAG, uri);

        imageView.setImageURI(Uri.parse(uri));
    }
}
