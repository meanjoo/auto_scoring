package com.example.autoscoringapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class TypeAnswerActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private EditText inputName, inputRow, inputCol, inputPoints, inputEssay;
    private Button btnMakeTable, btnSaveAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_answer);

        inputName = (EditText) findViewById(R.id.input_name);
        inputRow = (EditText) findViewById(R.id.input_row);
        inputCol = (EditText) findViewById(R.id.input_col);
        inputPoints = (EditText) findViewById(R.id.input_points);
        inputEssay = (EditText) findViewById(R.id.input_essay);
        btnMakeTable = (Button) findViewById(R.id.btn_make_table);
        btnSaveAnswer = (Button) findViewById(R.id.btn_save_answer);

        btnMakeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "표 만들기 버튼");
            }
        });

        btnSaveAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "저장하기 버튼 클릭");

                Log.d(TAG, "시험 이름: " + inputName.getText());
                Log.d(TAG, "행 크기: " + inputRow.getText());
                Log.d(TAG, "열 크기: " + inputCol.getText());
                Log.d(TAG, "문항 당 배점: " + inputPoints.getText());
                Log.d(TAG, "서술형: " + inputEssay.getText());
            }
        });
    }
}
