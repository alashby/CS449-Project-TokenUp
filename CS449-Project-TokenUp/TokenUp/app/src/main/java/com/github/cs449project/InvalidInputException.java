package com.github.cs449project;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by Al on 4/5/2018.
 */

public class InvalidInputException extends Exception {
    public InvalidInputException(String msg) {
        super(msg);
    }

    public void alert(Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("InvalidInputException Occurred");
        dialog.setMessage(this.toString());
        dialog.setNeutralButton("OK", null);
        dialog.show();
    }
}
