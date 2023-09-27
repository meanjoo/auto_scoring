package com.example.autoscoringapplication;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class TypeAnswerActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private EditText inputName, inputRow, inputCol, inputPoints, inputEssay;
    private Button btnMakeTable, btnSaveAnswer;
    private LinearLayout linearLayout;

    private int row, col;

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
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout);

        btnMakeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "표 만들기 버튼");
                linearLayout.removeAllViews();
                hideKeyboard();

                row = Integer.parseInt(inputRow.getText().toString());
                col = Integer.parseInt(inputCol.getText().toString());

                for(int i=0; i<row; i++) {
                    LinearLayout linearLayout2 = new LinearLayout(getApplicationContext()); // row linear layout
                    linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                    for(int j=0; j<col; j++) {
                        EditText editText = new EditText(getApplicationContext());
                        LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                        editTextParams.weight = 1;
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        editText.setLayoutParams(editTextParams);
                        editText.setBackgroundResource(R.drawable.edittext_border);
                        linearLayout2.addView(editText);
                    }
                    linearLayout.addView(linearLayout2);
                }
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

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
