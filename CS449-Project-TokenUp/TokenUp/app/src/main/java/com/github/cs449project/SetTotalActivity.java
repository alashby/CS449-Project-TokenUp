package com.github.cs449project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SetTotalActivity extends AppCompatActivity {

    int total;
    int attackers;
    int blockers;
    int ready;
    int ssick;
    int tapped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_total);

        total = attackers = blockers = ready = ssick = tapped = 0;
    }

    public void updateDialogText() {

        TextView totaltext=findViewById(R.id.text_dialogTotal);
        totaltext.setText(Integer.toString(total));

        TextView attackerstext=findViewById(R.id.text_dialogAttackers);
        attackerstext.setText(Integer.toString(attackers));

        TextView blockerstext=findViewById(R.id.text_dialogBlockers);
        blockerstext.setText(Integer.toString(blockers));

        TextView readytext=findViewById(R.id.text_dialogReady);
        readytext.setText(Integer.toString(ready));

        TextView ssicktext=findViewById(R.id.text_dialogSSick);
        ssicktext.setText(Integer.toString(ssick));

        TextView tappedtext=findViewById(R.id.text_dialogTapped);
        tappedtext.setText(Integer.toString(tapped));
    }


    public void incDialogAttackers(View view) {
        attackers++;
        total++;
        updateDialogText();
    }

    public void incDialogBlockers(View view) {
        blockers++;
        total++;
        updateDialogText();
    }

    public void incDialogReady(View view) {
        ready++;
        total++;
        updateDialogText();
    }

    public void incDialogSSick(View view) {
        ssick++;
        total++;
        updateDialogText();
    }

    public void incDialogTapped(View view) {
        tapped++;
        total++;
        updateDialogText();
    }


    public void decDialogAttackers(View view) {
        if (attackers > 0) {
            attackers--;
            total--;
            updateDialogText();
        }
    }

    public void decDialogBlockers(View view) {
        if (blockers > 0) {
            blockers--;
            total--;
            updateDialogText();
        }
    }

    public void decDialogReady(View view) {
        if (ready > 0) {
            ready--;
            total--;
            updateDialogText();
        }
    }

    public void decDialogSSick(View view) {
        if (ssick > 0) {
            ssick--;
            total--;
            updateDialogText();
        }
    }

    public void decDialogTapped(View view) {
        if (tapped > 0) {
            tapped--;
            total--;
            updateDialogText();
        }
    }

    public void confirm(View view) {
        MainActivity.mainact.finish();

        Intent mainScreen = new Intent(SetTotalActivity.this, MainActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt("TOTAL", total);
        bundle.putInt("ATTACKERS", attackers);
        bundle.putInt("BLOCKERS", blockers);
        bundle.putInt("READY", ready);
        bundle.putInt("SSICK", ssick);
        bundle.putInt("TAPPED", tapped);
        bundle.putBoolean("SETTOTAL", true);
        mainScreen.putExtras(bundle);

        startActivity(mainScreen);

        finish();
    }

    public void cancel(View view){
        finish();
    }
}
