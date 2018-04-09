package com.github.cs449project;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private static int power = 0;
    private static int toughness = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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

    public void clickRadioBtn(View view) {
        List<RadioButton> rbtns = Arrays.asList((RadioButton) findViewById(R.id.button_monocolor),
                (RadioButton) findViewById(R.id.button_multicolor),
                (RadioButton) findViewById(R.id.button_bothcolor));

        for (int i = 0; i < rbtns.size(); i++) {
            if (view != rbtns.get(i)){
                rbtns.get(i).setChecked(false);
            }
        }

    }

    public void search(View view) {
        RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.relativelayout);

        Bundle bundle = new Bundle();

        for (int i = 0; i < rlayout.getChildCount(); i++) {
            View child = rlayout.getChildAt(i);

            String instance = getResources().getResourceEntryName(child.getId());
            if (child instanceof TableLayout) {
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

                    if (lchild instanceof RadioButton) {
                        if (((RadioButton) lchild).isChecked()) {
                            bundle.putBoolean(getResources().getResourceEntryName(lchild.getId()), true);
                        }
                    }
                    else if (lchild instanceof TextView && (getResources().getResourceEntryName(lchild.getId()).equals("text_power")
                            || getResources().getResourceEntryName(lchild.getId()).equals("text_toughness")) ) {
                        if (!((TextView) lchild).getText().toString().equals("Any")) {
                            bundle.putString(getResources().getResourceEntryName(lchild.getId()), ((TextView) lchild).getText().toString());
                        }
                    }
                }
            }
            else if (child instanceof EditText) {
                if (isSelected(((EditText) child))) {
                    if (getResources().getResourceEntryName(child.getId()).equals("input_abilities")) {
                        String abilities[] = ((EditText) child).getText().toString().split(";");
                        for (int j = 0; j < abilities.length; j++) {
                            abilities[j] = abilities[j].trim();
                        }
                        bundle.putStringArray(getResources().getResourceEntryName(child.getId()), abilities);

                    }
                    else if (getResources().getResourceEntryName(child.getId()).equals("input_tags")) {
                        String tags[] = ((EditText) child).getText().toString().split(";");
                        for (int j = 0; j < tags.length; j++) {
                            tags[j] = tags[j].trim();
                        }
                        bundle.putStringArray(getResources().getResourceEntryName(child.getId()), tags);

                    }
                    else {
                        bundle.putString(getResources().getResourceEntryName(child.getId()), ((EditText) child).getText().toString().trim());
                    }
                }
            }
        }

        Intent searchactivity = new Intent(SearchActivity.this, FilteredBrowseActivity.class);
        searchactivity.putExtras(bundle);

        startActivity(searchactivity);

        this.finish();
    }

    public void checkColor(View view) {
        List<CheckBox> boxes = Arrays.asList( (CheckBox) findViewById(R.id.checkbox_black),
                (CheckBox) findViewById(R.id.checkbox_blue),
                (CheckBox) findViewById(R.id.checkbox_green),
                (CheckBox) findViewById(R.id.checkbox_red),
                (CheckBox) findViewById(R.id.checkbox_white),
                (CheckBox) findViewById(R.id.checkbox_artifact),
                (CheckBox) findViewById(R.id.checkbox_colorless));
        int checkedcount = 0;

        for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).isChecked()) {
                checkedcount++;
            }
        }

        RadioButton monocolor = (RadioButton) findViewById(R.id.button_monocolor);
        RadioButton multicolor = (RadioButton) findViewById(R.id.button_multicolor);
        RadioButton bothcolor = (RadioButton) findViewById(R.id.button_bothcolor);

        if (checkedcount > 1) {
            monocolor.setEnabled(true);
            multicolor.setEnabled(true);
            bothcolor.setEnabled(true);
        }
        if (checkedcount <= 1) {
            if (checkedcount == 1) {
                monocolor.setChecked(true);
                monocolor.setEnabled(true);

                bothcolor.setChecked(false);
                bothcolor.setEnabled(true);
            }
            else {
                monocolor.setChecked(false);
                monocolor.setEnabled(false);

                bothcolor.setChecked(false);
                bothcolor.setEnabled(false);
            }

            multicolor.setChecked(false);
            multicolor.setEnabled(false);
        }

    }
}