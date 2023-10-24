package com.example.autoscoringapplication.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Answer {
    @Expose
    @SerializedName("ans_name") private String ansName;
    @SerializedName("row_size") private int rowSize;
    @SerializedName("col_size") private int colSize;
    @SerializedName("score") private int score;
    @SerializedName("obj_ans") private String objAns;
    @SerializedName("sub_ans") private String subAns;

    public void setAnsName(String ansName) { this.ansName = ansName; }
    public String getAnsName() { return ansName; }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }
    public int getRowSize() {
        return rowSize;
    }

    public void setColSize(int colSize) {
        this.colSize = colSize;
    }
    public int getColSize() {
        return colSize;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }

    public void setObjAns(String objAns) { this.objAns = objAns; }
    public String getObjAns() { return objAns; }

    public void setSubAns(String subAns) { this.subAns = subAns; }
    public String getSubAns() { return subAns; }
}
