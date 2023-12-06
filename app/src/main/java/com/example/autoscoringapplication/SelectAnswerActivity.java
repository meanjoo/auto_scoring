package com.example.autoscoringapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autoscoringapplication.api.MyAPI;
import com.example.autoscoringapplication.data.Name;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectAnswerActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private final String hint = "채점할 답안지의 답안을 선택하세요.";
    private Spinner spinner;
    private MyAPI api;
    private Button btnSelectOk, btnAddAnswer;
    private String selectId;
    private List<Name> names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_answer);

        api = ApiClient.getRetrofit().create(MyAPI.class);
        spinner = (Spinner) findViewById(R.id.spinner_answer);
        btnSelectOk = (Button) findViewById(R.id.btn_select_ok);
        btnAddAnswer = (Button) findViewById(R.id.btn_add_answer);
        btnSelectOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectAnswerActivity.this, ResultActivity.class);
                intent.putExtra("id", selectId);
                startActivity(intent);
                finish();
            }
        });

        btnAddAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectAnswerActivity.this, TypeAnswerActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        api.getName().enqueue(new Callback<List<Name>>() {
            @Override
            public void onResponse(Call<List<Name>> call, Response<List<Name>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "success");
                    names = response.body();
                    ArrayList<String> nameList = new ArrayList<>();
                    nameList.add(hint);
                    for(Name a : names){
                        nameList.add(a.getAnsName());
                    }

                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            nameList);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(spinnerAdapter);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(!nameList.get(position).equals(hint)){
                                selectId = names.get(position - 1).getId();
//                                Toast.makeText(getApplicationContext(), nameList.get(position)+"가 선택", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "id: " + names.get(position-1).getId() + "ansName: " + names.get(position - 1 ).getAnsName());
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Name>> call, Throwable t) {

            }
        });
    }
}
