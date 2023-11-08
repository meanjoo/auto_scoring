package com.example.autoscoringapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private int answerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        answerId = intent.getIntExtra("id", -1);
        Log.d(TAG, "id: " + answerId);
    }
}
