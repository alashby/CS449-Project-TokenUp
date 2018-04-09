package com.github.cs449project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateTokenActivity extends AppCompatActivity {

    private static int power = 0;
    private static int toughness = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_token);


    }

    private boolean isSelected(EditText field) {
        String fieldcontents = field.getText().toString();
        fieldcontents = fieldcontents.trim();
        return !fieldcontents.isEmpty();
    }

    public void incPower(View view) {
        TextView powertxt = (TextView) findViewById(R.id.text_power);
        String currentpower = powertxt.getText().toString();
        switch (currentpower) {
            case "-1": power++; powertxt.setText("Any"); break;
            case "Any": powertxt.setText("*"); break;
            case "*": powertxt.setText("0"); break;
            default: power++; powertxt.setText(Integer.toString(power)); break;
        }
    }

    public void decPower(View view) {
        TextView powertxt = (TextView) findViewById(R.id.text_power);
        String currentpower = powertxt.getText().toString();
        switch (currentpower) {
            case "0": powertxt.setText("*"); break;
            case "*": powertxt.setText("Any"); break;
            default: power--; powertxt.setText(Integer.toString(power)); break;
        }
    }

    public void incToughness(View view) {
        TextView toughnesstxt = (TextView) findViewById(R.id.text_toughness);
        String currenttoughness = toughnesstxt.getText().toString();
        switch (currenttoughness) {
            case "-1": toughness++; toughnesstxt.setText("Any"); break;
            case "Any": toughnesstxt.setText("*"); break;
            case "*": toughnesstxt.setText("0"); break;
            default: toughness++; toughnesstxt.setText(Integer.toString(toughness)); break;
        }
    }

    public void decToughness(View view) {
        TextView toughnesstxt = (TextView) findViewById(R.id.text_toughness);
        String currenttoughness = toughnesstxt.getText().toString();
        switch (currenttoughness) {
            case "0": toughnesstxt.setText("*"); break;
            case "*": toughnesstxt.setText("Any"); break;
            default: toughness--; toughnesstxt.setText(Integer.toString(toughness)); break;
        }
    }



    public void create(View view) throws InvalidInputException {
        RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.relativelayout);

        Bundle bundle = new Bundle();
        ArrayList<String> colors = new ArrayList<>();

        for (int i = 0; i < rlayout.getChildCount(); i++) {
            View child = rlayout.getChildAt(i);
            if (child instanceof EditText) {
                if (getResources().getResourceName(child.getId()) == "input_id") {
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
                    databaseAccess.open();
                    if ( databaseAccess.getIds().contains(((EditText) child).getText().toString())) {
                        throw new InvalidInputException("ID already in database");
                    }
                    else if(((EditText) child).getText().toString().isEmpty()) {
                        throw new InvalidInputException("ID not defined");
                    }
                    databaseAccess.close();
                }
                bundle.putString(getResources().getResourceName(child.getId()), ((EditText) child).getText().toString());
            }
            else if (child instanceof TableLayout) {
                TableLayout tlayout = (TableLayout) findViewById(R.id.colorslayout);

                for (int j = 0; j < tlayout.getChildCount(); j++) {
                    View row = tlayout.getChildAt(j);
                    if (row instanceof TableRow) {
                        for (int k = 0; k < ((TableRow) row).getChildCount(); k++) {

                            View rowchild = ((TableRow) row).getChildAt(k);

                            if (rowchild instanceof CheckBox) {
                                if (((CheckBox) rowchild).isChecked()) {
                                    bundle.putBoolean(((CheckBox) rowchild).getText().toString().toUpperCase(), true);
                                }
                            }
                        }
                    }
                }
            }
            else if (child instanceof LinearLayout) {
                LinearLayout llayout = ((LinearLayout) child);
                for (int j = 0; j < llayout.getChildCount(); j++) {
                    View lchild = llayout.getChildAt(j);

                    if (lchild instanceof TextView && (getResources().getResourceEntryName(lchild.getId()).equals("text_power")
                            || getResources().getResourceEntryName(lchild.getId()).equals("text_toughness")) ) {
                        if (!((TextView) lchild).getText().toString().equals("Any")) {
                            bundle.putString(getResources().getResourceEntryName(lchild.getId()), ((TextView) lchild).getText().toString());
                        }
                    }
                }
            }
        }

        if (!colors.isEmpty()) {
            bundle.putStringArrayList("COLORS", colors);
        }

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        databaseAccess.createToken(bundle);

        finish();
    }

}
