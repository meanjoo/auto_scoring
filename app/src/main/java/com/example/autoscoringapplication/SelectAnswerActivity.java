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
import com.example.autoscoringapplication.api.TestAPI;
import com.example.autoscoringapplication.data.Album;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectAnswerActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private final String hint = "채점할 답안지의 답안을 선택하세요.";
    private Spinner spinner;
    private TestAPI testApi;
    private List<Album> albums;
    private Button btnSelectOk;
    private int selectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_answer);

        testApi = TestApiClient.getRetrofit().create(TestAPI.class);
        spinner = (Spinner) findViewById(R.id.spinner_answer);
        btnSelectOk = (Button) findViewById(R.id.btn_select_ok);

        testApi.getAlbum().enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "success");
                    albums = response.body();
                    ArrayList<String> answerList = new ArrayList<>();
                    answerList.add(hint); // hint
                    for (Album a : albums) {
//                        Log.d(TAG, a.getTitle());
                        answerList.add(a.getTitle());
                    }

                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            answerList);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(spinnerAdapter);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (!answerList.get(position).equals(hint)) {
                                selectId = albums.get(position - 1).getId();
                                Toast.makeText(getApplicationContext(), answerList.get(position)+"가 선택", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "id: " + albums.get(position - 1).getId() +", title: " + albums.get(position - 1).getTitle());
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                Log.d(TAG, "onFailure");
                t.printStackTrace();
            }
        });

        btnSelectOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectAnswerActivity.this, ResultActivity.class);
                intent.putExtra("id", selectId);
                startActivity(intent);
                finish();
            }
        });
    }
}
