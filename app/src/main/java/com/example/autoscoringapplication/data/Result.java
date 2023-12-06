package com.example.autoscoringapplication.data;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("name") private String name;
    @SerializedName("score") private int score;
    @SerializedName("total_score") private int totalScore;
    @SerializedName("obj_result") private String objResult;
    @SerializedName("similarity") private double similarity;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setObjResult(String objResult) {
        this.objResult = objResult;
    }

    public String getObjResult() {
        return objResult;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public double getSimilarity() {
        return similarity;
    }
}
