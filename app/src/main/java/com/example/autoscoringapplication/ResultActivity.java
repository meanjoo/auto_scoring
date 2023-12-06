package com.example.autoscoringapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autoscoringapplication.api.MyAPI;
import com.example.autoscoringapplication.data.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {
    private final int titleSize = 20;
    private final int textSize = 16;
    private final String TAG = this.getClass().getSimpleName();
    private String id;
    private MyAPI api;
    private Result result;
    private LinearLayout scoreLayout, scoreDetailLayout, similarityLayout;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        api = ApiClient.getRetrofit().create(MyAPI.class);
        scoreLayout = (LinearLayout) findViewById(R.id.layout_score);
        scoreDetailLayout = (LinearLayout) findViewById(R.id.layout_score_detail);
        similarityLayout = (LinearLayout) findViewById(R.id.layout_similarity);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Log.d(TAG, "id: " + id);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false); // 주변 터치 방지
        progressDialog.setCancelable(false); // 뒤로가기 방지
        progressDialog.show();

        api.getResult(id).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "success");
                    progressDialog.cancel();
//                    findViewById(R.id.txt_score_title).setVisibility(View.VISIBLE);
//                    findViewById(R.id.txt_essay_title).setVisibility(View.VISIBLE);
//                    findViewById(R.id.score_separator).setVisibility(View.VISIBLE);
//                    findViewById(R.id.txt_similarity_title).setVisibility(View.VISIBLE);

                    result = response.body();
                    String[] objResult = result.getObjResult().split("");
                    double similarity = result.getSimilarity();
                    ((TextView) findViewById(R.id.txt_name))
                            .setText(result.getName());

                    addDynamicTextView(getApplicationContext(), scoreLayout, "점수    ", titleSize, "", "");
                    addDynamicTextView(getApplicationContext(), scoreLayout, String.valueOf(result.getScore()), 20, "blue", "bold");
                    addDynamicTextView(getApplicationContext(), scoreLayout, "/", 20, "", "");
                    addDynamicTextView(getApplicationContext(), scoreLayout, String.valueOf(result.getTotalScore()), 20, "", "");

//                    ((TextView) findViewById(R.id.txt_my_score))
//                            .setText(String.valueOf(result.getScore()));
//                    ((TextView) findViewById(R.id.txt_total_score))
//                            .setText(String.valueOf(result.getTotalScore()));

                    addDynamicTextView(getApplicationContext(), scoreDetailLayout, "세부 채점 결과", titleSize, "", "");
                    for (int i=0; i< objResult.length; i++) {
                        TextView textView = new TextView(getApplicationContext());
                        String text = "Q" + (i+1) + ". " + objResult[i];
                        SpannableString spannableString = new SpannableString(text);
                        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        if (objResult[i].equals("X")) {
                            spannableString.setSpan(new ForegroundColorSpan(Color.RED), text.length()-1, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                        textView.setText(spannableString);
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                        textView.setLineSpacing(8.0f, 1.5f);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );
                        scoreDetailLayout.addView(textView, params);
                    }

                    addDynamicTextView(getApplicationContext(), similarityLayout, "서술형 채점 결과", titleSize, "", "");
                    addDynamicTextView(getApplicationContext(), similarityLayout, String.valueOf(similarity) + "%", 20, "blue", "bold");
                    addDynamicTextView(getApplicationContext(), similarityLayout, "", 10, "", "");
                    if (similarity >= 75.0f) {
                        addDynamicTextView(getApplicationContext(), similarityLayout, "⭕ 정답 처리", 20, "", "");
                    } else if (similarity < 60.f) {
                        addDynamicTextView(getApplicationContext(), similarityLayout, "❌ 오답 처리", 20, "", "");
                    } else {
                        addDynamicTextView(getApplicationContext(), similarityLayout, "⚠️ 채점자의 확인 필요", 20, "", "");
                    }


                    Log.d(TAG, result.getObjResult());
                    Log.d(TAG, String.valueOf(result.getSimilarity()));
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d(TAG, "onFailure");
                t.printStackTrace();
            }
        });
    }

    private void addDynamicTextView(Context context, LinearLayout linearLayout, String text, float textSize, String color, String style) {
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        textView.setLayoutParams(layoutParams);
        textView.setText(text);

        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

        switch(color) {
            case "blue":
                textView.setTextColor(Color.BLUE);
                break;
            default:
                textView.setTextColor(Color.BLACK);
        }

        switch(style) {
            case "bold":
                textView.setTypeface(null, Typeface.BOLD);
                break;
            default:;
        }

        linearLayout.addView(textView);
    }
}