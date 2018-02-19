package com.github.cs449project;

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
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String fName = getIntent().getStringExtra("SelectedToken");
        String path = Environment.getExternalStorageDirectory() + fName + ".png";
        Bitmap bm = BitmapFactory.decodeFile(path);

        ImageView imageView = (ImageView) findViewById(R.id.background);

        if (SelectedToken.img != null) {
            Drawable drawable = new BitmapDrawable(getResources(), SelectedToken.img);
            imageView.setBackground(drawable);
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



}
