package com.github.cs449project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseAccess databaseAccess;
    public static Activity mainact;

    Tokens tokens = new Tokens();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainact = this;

        ImageView imageView = (ImageView) findViewById(R.id.background);

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void disableButtons() {
        Button incTotalbtn = (Button)findViewById(R.id.button_incTotal);
        incTotalbtn.setEnabled(false);
        Button decTotalbtn = (Button)findViewById(R.id.button_decTotal);
        decTotalbtn.setEnabled(false);
    }

    private void enableButtons() {
        Button incTotalbtn = (Button)findViewById(R.id.button_incTotal);
        incTotalbtn.setEnabled(true);
        Button decTotalbtn = (Button)findViewById(R.id.button_decTotal);
        decTotalbtn.setEnabled(true);
    }

    public void updateText() {
        TextView totaltext=(TextView)findViewById(R.id.button_Total);
        totaltext.setText("Total: "+tokens.getTotalTokens());

        TextView ssicktext=(TextView)findViewById(R.id.button_summoningSick);
        ssicktext.setText(Integer.toString(tokens.getSummoningSick()));

        TextView readytext=findViewById(R.id.button_Ready);
        readytext.setText(Integer.toString(tokens.getReadyTokens()));
    }

    public void incTotal(View view) {
        tokens.incTotal();
        updateText();
    }

    public void decTotal(View view) {
        tokens.decTotal();
        updateText();
    }




}
