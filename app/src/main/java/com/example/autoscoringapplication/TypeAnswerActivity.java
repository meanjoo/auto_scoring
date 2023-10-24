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

import com.example.autoscoringapplication.api.MyAPI;
import com.example.autoscoringapplication.data.Answer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TypeAnswerActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    private EditText inputName, inputRow, inputCol, inputPoints, inputEssay;
    private Button btnMakeTable, btnSaveAnswer;
    private LinearLayout linearLayout;
    private MyAPI api;

    private int row, col;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_answer);

        api = ApiClient.getRetrofit().create(MyAPI.class);
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

                for(int i=1; i<=row; i++) {
                    LinearLayout linearLayout2 = new LinearLayout(getApplicationContext()); // row linear layout
                    linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                    for(int j=0; j<col; j++) {
                        EditText editText = new EditText(getApplicationContext());
                        LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                        editTextParams.weight = 1;
                        editText.setLayoutParams(editTextParams);
                        editText.setId(i+j*row);
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
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

                Answer answer = new Answer();

//                Log.d(TAG, "시험 이름: " + inputName.getText());
//                Log.d(TAG, "행 크기: " + inputRow.getText());
//                Log.d(TAG, "열 크기: " + inputCol.getText());
//                Log.d(TAG, "문항 당 배점: " + inputPoints.getText());
//                Log.d(TAG, "서술형: " + inputEssay.getText());

                answer.setAnsName(inputName.getText().toString());
                answer.setRowSize(Integer.parseInt(inputRow.getText().toString()));
                answer.setColSize(Integer.parseInt(inputCol.getText().toString()));
                answer.setScore(Integer.parseInt(inputPoints.getText().toString()));
                answer.setSubAns(inputEssay.getText().toString());

                List<String> objAns = new ArrayList<>();
                for(int i=1; i<=row*col; i++) {
                    EditText editText = (EditText) findViewById(i);
//                    Log.d(TAG, "표 내용 " + Integer.toString(i) + " : " + editText.getText().toString());
                    objAns.add(editText.getText().toString());
                }
                answer.setObjAns(String.join(",", objAns));

                api.postAnswer(answer).enqueue(new Callback<Answer>() {
                    @Override
                    public void onResponse(Call<Answer> call, Response<Answer> response) {
                        if (response.isSuccessful()) { // http response code가 200일 때
                            Log.d(TAG, "서버 전달 성공!");
                        }
                    }

                    @Override
                    public void onFailure(Call<Answer> call, Throwable t) {
                        Log.d(TAG, "onFailure");
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
