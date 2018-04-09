package com.github.cs449project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseAccess databaseAccess;
    public static Activity mainact;

    Tokens tokens;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainact = this;

        ImageView imageView = (ImageView) findViewById(R.id.background);
        tokens = new Tokens();

        if (SelectedToken.img != null) {
            enableButtons();

            Drawable drawable = new BitmapDrawable(getResources(), SelectedToken.img);
            imageView.setBackground(drawable);

            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();

            SelectedToken.abilities = databaseAccess.getAbilities(SelectedToken.id);

            databaseAccess.close();
        }
        else {
            disableButtons();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_selecttoken:
                Intent activityBrowse = new Intent(MainActivity.this, BrowseByActivity.class);
                startActivity(activityBrowse);
                return true;
            case R.id.menu_createtoken:
                Intent activityCreateToken = new Intent(MainActivity.this, CreateTokenActivity.class);
                startActivity(activityCreateToken);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setTotal (View view) {
        Intent activitySetTotal = new Intent(MainActivity.this, SetTotalActivity.class);
        startActivity(activitySetTotal);
    }

    private void disableButtons() {
        Button incTotalbtn = findViewById(R.id.button_incTotal);
        incTotalbtn.setEnabled(false);
        Button decTotalbtn = findViewById(R.id.button_decTotal);
        decTotalbtn.setEnabled(false);
        Button newUpkeepbtn = findViewById(R.id.button_newUpkeep);
        newUpkeepbtn.setEnabled(false);
        Button passTurnbtn = findViewById(R.id.button_passTurn);
        passTurnbtn.setEnabled(false);
        Button totalbtn = findViewById(R.id.button_Total);
        totalbtn.setEnabled(false);
    }

    private void enableButtons() {
        Button incTotalbtn = findViewById(R.id.button_incTotal);
        incTotalbtn.setEnabled(true);
        Button decTotalbtn = findViewById(R.id.button_decTotal);
        decTotalbtn.setEnabled(true);
        Button newUpkeepbtn = findViewById(R.id.button_newUpkeep);
        newUpkeepbtn.setEnabled(true);
        Button passTurnbtn = findViewById(R.id.button_passTurn);
        passTurnbtn.setEnabled(true);
        Button totalbtn = findViewById(R.id.button_Total);
        totalbtn.setEnabled(true);
    }

    public void updateText() {
        TextView totaltext=findViewById(R.id.button_Total);
        totaltext.setText("Total: "+tokens.getTotalTokens());

        TextView attackerstext = findViewById(R.id.button_Attackers);
        attackerstext.setText(Integer.toString(tokens.getAttackers()));

        TextView blockerstext = findViewById(R.id.button_Blockers);
        blockerstext.setText(Integer.toString(tokens.getBlockers()));

        TextView readytext = findViewById(R.id.button_Ready);
        readytext.setText(Integer.toString(tokens.getReadyTokens()));

        TextView ssicktext = findViewById(R.id.button_summoningSick);
        ssicktext.setText(Integer.toString(tokens.getSummoningSick()));

        TextView tappedtext = findViewById(R.id.button_Tapped);
        tappedtext.setText(Integer.toString(tokens.getTapped()));
    }

    public void incTotal(View view) {
        clearMenus(view);
        tokens.incTotal();
        updateText();
    }

    public void decTotal(View view) {
        clearMenus(view);
        tokens.decTotal();
        updateText();
    }

    public void newUpkeep(View view) {
        clearMenus(view);
        tokens.newUpkeep();
        updateText();
    }

    public void passTurn(View view) {
        clearMenus(view);
        tokens.opponentTurn();
        updateText();
    }

public void buttonClick(View view) {
        switch(view.getId()) {
            case R.id.button_attackerInc:
                tokens.incAttackers(); break;
            case R.id.button_attackerDec:
                tokens.decAttackers(); break;
            case R.id.button_attackerDie:
                tokens.kill("attackers"); break;
            case R.id.button_attackerTap:
                tokens.tap("attackers"); break;

            case R.id.button_blockerInc:
                tokens.incBlockers(); break;
            case R.id.button_blockerDec:
                tokens.decBlockers(); break;
            case R.id.button_blockerDie:
                tokens.kill("blockers"); break;
            case R.id.button_blockerTap:
                tokens.tap("blockers"); break;

            case R.id.button_readyInc:
                tokens.incReady(); break;
            case R.id.button_readyDec:
                tokens.tap("ready"); break;
            case R.id.button_readyDie:
                tokens.kill("ready"); break;
            case R.id.button_readyTap:
                tokens.tap("ready"); break;

            case R.id.button_sSickDie:
                tokens.kill("ssick"); break;
            case R.id.button_sSickTap:
                tokens.tap("ssick"); break;

            case R.id.button_tappedDie:
                tokens.kill("tapped"); break;
            case R.id.button_tappedUntap:
                tokens.incReady(); break;
        }

        updateText();
}

    public void expandAttackers(View view) {
        clearMenus(view);
        View attackersMenu = findViewById(R.id.attackersMenu);
        attackersMenu.setVisibility(View.VISIBLE);
    }

    public void expandBlockers(View view) {
        clearMenus(view);
        View blockersMenu = findViewById(R.id.blockersMenu);
        blockersMenu.setVisibility(View.VISIBLE);
    }

    public void expandReady(View view) {
        clearMenus(view);
        View readyMenu = findViewById(R.id.readyMenu);
        readyMenu.setVisibility(View.VISIBLE);
    }

    public void expandSSick(View view) {
        clearMenus(view);
        View sSickMenu = findViewById(R.id.sSickMenu);
        sSickMenu.setVisibility(View.VISIBLE);
    }

    public void expandTapped(View view) {
        clearMenus(view);
        View tappedMenu = findViewById(R.id.tappedMenu);
        tappedMenu.setVisibility(View.VISIBLE);
    }

    public void expandEffects(View view) {
        clearMenus(view);
        View effectsFragment = findViewById(R.id.effectsLayout);
        effectsFragment.setVisibility(View.VISIBLE);
    }

    public void clearMenus(View view) {
        View attackersMenu = findViewById(R.id.attackersMenu);
        attackersMenu.setVisibility(View.INVISIBLE);

        View blockersMenu = findViewById(R.id.blockersMenu);
        blockersMenu.setVisibility(View.INVISIBLE);

        View readyMenu = findViewById(R.id.readyMenu);
        readyMenu.setVisibility(View.INVISIBLE);

        View sSickMenu = findViewById(R.id.sSickMenu);
        sSickMenu.setVisibility(View.INVISIBLE);

        View tappedMenu = findViewById(R.id.tappedMenu);
        tappedMenu.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();


        if (getIntent().hasExtra("CREATEEFFECT")) {
            Bundle bundle = getIntent().getExtras();
            ArrayList<String> keywords = bundle.getStringArrayList("KEYWORDS");
            int numAffected = bundle.getInt("NUMAFFECTED");
            String descstr = bundle.getString("DESCSTR");
            String srcstr = bundle.getString("SRCSTR");

            getIntent().removeExtra("CREATEEFFECT");

            EffectsFragment effectsfrag = (EffectsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_effects);
            Effect effect = new Effect(keywords, numAffected, descstr, srcstr);
            effectsfrag.addEffect(effect);
        }

        if (getIntent().hasExtra("SETTOTAL")) {
            Bundle bundle = getIntent().getExtras();
            tokens.setTotalTokens(bundle.getInt("TOTAL"));
            tokens.setAttackers(bundle.getInt("ATTACKERS"));
            tokens.setBlockers(bundle.getInt("BLOCKERS"));
            tokens.setReadyTokens(bundle.getInt("READY"));
            tokens.setSummoningSick(bundle.getInt("SSICK"));
            tokens.setTapped(bundle.getInt("TAPPED"));

            getIntent().removeExtra("SETTOTAL");

            updateText();
        }
    }

}
