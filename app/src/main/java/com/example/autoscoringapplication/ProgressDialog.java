package com.example.autoscoringapplication;

import android.app.Dialog;
import android.content.Context;

public class ProgressDialog extends Dialog {
    public ProgressDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_progress);
    }
}
