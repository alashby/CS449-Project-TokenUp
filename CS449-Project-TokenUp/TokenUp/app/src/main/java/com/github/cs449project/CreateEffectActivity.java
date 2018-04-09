package com.github.cs449project;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CreateEffectActivity extends AppCompatActivity {

    private int power = 0;
    private int toughness = 0;
    private int numAffected = 0;
    private String effectDesc = "N/A";
    private String srcCard = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_effect);
    }

    public void confirm(View view) {
        ArrayList<String> keywords = new ArrayList<String>();


        ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>() {
            {
                add((CheckBox) findViewById(R.id.checkbox_deathtouch));
                add((CheckBox) findViewById(R.id.checkbox_doubleStrike));
                add((CheckBox) findViewById(R.id.checkbox_firstStrike));
                add((CheckBox) findViewById(R.id.checkbox_fear));
                add((CheckBox) findViewById(R.id.checkbox_flying));
                add((CheckBox) findViewById(R.id.checkbox_haste));
                add((CheckBox) findViewById(R.id.checkbox_hexproof));
                add((CheckBox) findViewById(R.id.checkbox_indestructible));
                add((CheckBox) findViewById(R.id.checkbox_lifelink));
                add((CheckBox) findViewById(R.id.checkbox_menace));
                add((CheckBox) findViewById(R.id.checkbox_reach));
                add((CheckBox) findViewById(R.id.checkbox_shroud));
                add((CheckBox) findViewById(R.id.checkbox_trample));
                add((CheckBox) findViewById(R.id.checkbox_vigilance));
            }};
        CheckBox otherBox = (CheckBox) findViewById(R.id.checkbox_other);
        CheckBox protectionBox = (CheckBox) findViewById(R.id.checkbox_protection);
        CheckBox counterBox = (CheckBox) findViewById(R.id.checkbox_counter);

        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isChecked()) {
                keywords.add(checkBoxes.get(i).getText().toString());
            }
        }

        if (otherBox.isChecked()) {
            EditText othertxt = (EditText) findViewById(R.id.input_otherKeyword);
            keywords.add(othertxt.getText().toString());
        }

        if (protectionBox.isChecked()) {
            EditText protecttxt = (EditText) findViewById(R.id.input_protectionFrom);
            String protectionFrom = "Protection from " + protecttxt.getText().toString();
            keywords.add(protectionFrom);
        }

        if (counterBox.isChecked()) {
            String countertxt = "";

            if (power >= 0) {
                countertxt += "+";
            }
            countertxt += Integer.toString(power) + "/";

            if (toughness >= 0) {
                countertxt += "+";
            }
            countertxt += Integer.toString(toughness);

            keywords.add(countertxt);
        }

        EditText desctxt = (EditText) findViewById(R.id.input_effectDesc);
        String descstr = desctxt.getText().toString();
        EditText srctxt = (EditText) findViewById((R.id.input_effectSrc));
        String srcstr = srctxt.getText().toString();

        if (descstr.isEmpty()) {
            descstr = "N/A";
        }
        if (srcstr.isEmpty()) {
            srcstr = "N/A";
        }

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("KEYWORDS", keywords);
        bundle.putInt("NUMAFFECTED", numAffected);
        bundle.putBoolean("CREATEEFFECT", true);

        MainActivity.mainact.getIntent().putExtras(bundle);

        finish();
    }

    public void cancel(View view){
        finish();
    }

    public void updatePower() {
        TextView powertxt = (TextView) findViewById(R.id.text_power);
        powertxt.setText(Integer.toString(power));
    }

    public void updateToughness() {
        TextView toughnesstxt = (TextView) findViewById(R.id.text_toughness);
        toughnesstxt.setText(Integer.toString(toughness));
    }

    public void updateNumAffected() {
        TextView numtxt = (TextView) findViewById(R.id.text_num);
        numtxt.setText(Integer.toString(numAffected));
    }

    public void incPower(View view) {
        power++;
        updatePower();
    }

    public void decPower(View view) {
        power--;
        updatePower();
    }

    public void incToughness(View view) {
        toughness++;
        updateToughness();
    }

    public void decToughness(View view) {
        toughness--;
        updateToughness();
    }

    public void incNumAffected(View view) {
        numAffected++;
        updateNumAffected();
    }

    public void decNumAffected(View view) {
        if (numAffected > 0) {
            numAffected--;
            updateNumAffected();
        }
    }

    public void toggleCounterBtns(View view) {
        CheckBox counterBox = (CheckBox) findViewById(R.id.checkbox_counter);
        Button pwrBtnInc = (Button) findViewById(R.id.button_powerInc);
        Button pwrBtnDec = (Button) findViewById(R.id.button_powerDec);
        Button toughBtnInc = (Button) findViewById(R.id.button_toughnessInc);
        Button toughBtnDec = (Button) findViewById(R.id.button_toughnessDec);

        if (counterBox.isChecked()) {
            pwrBtnInc.setEnabled(true);
            pwrBtnDec.setEnabled(true);
            toughBtnInc.setEnabled(true);
            toughBtnDec.setEnabled(true);
        }
        else {
            pwrBtnInc.setEnabled(false);
            pwrBtnDec.setEnabled(false);
            toughBtnInc.setEnabled(false);
            toughBtnDec.setEnabled(false);
        }
    }

    public void enableNumAffectedBtns(View view) {
        RadioButton allAffectedBtn = (RadioButton) findViewById(R.id.button_allAffected);
        Button numAffectedInc = (Button) findViewById(R.id.button_numInc);
        Button numAffectedDec = (Button) findViewById(R.id.button_numDec);

        if (allAffectedBtn.isChecked()) {
            allAffectedBtn.setChecked(false);
        }
        numAffectedInc.setEnabled(true);
        numAffectedDec.setEnabled(true);
    }

    public void disableNumAffectedBtns(View view) {
        RadioButton numAffectedBtn = (RadioButton) findViewById(R.id.button_numAffected);
        Button numAffectedInc = (Button) findViewById(R.id.button_numInc);
        Button numAffectedDec = (Button) findViewById(R.id.button_numDec);

        if (numAffectedBtn.isChecked()) {
            numAffectedBtn.setChecked(false);
        }
        numAffectedInc.setEnabled(false);
        numAffectedDec.setEnabled(false);

        numAffected = 0;
    }
}
