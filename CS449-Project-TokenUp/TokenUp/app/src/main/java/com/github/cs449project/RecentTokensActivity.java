package com.github.cs449project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class RecentTokensActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_tokens);

        File recentTokensFile = getFileStreamPath("recenttokens.txt");
        FileInputStream fistream = null;
        BufferedReader freader;

        ArrayList<String> ids = new ArrayList<>();

        try {
            fistream = new FileInputStream(recentTokensFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        freader = new BufferedReader(new InputStreamReader(fistream));
        String line = "";
        try {
            while (line != null) {
                if (!line.isEmpty()) {
                    ids.add(line);
                }
                line = freader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!ids.isEmpty()) {
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();

            ArrayList<Bitmap> imgs = new ArrayList<>();

            for (int i = 0; i < ids.size(); i++) {
                imgs.add(databaseAccess.getImgs(ids.get(i), "Id").get(0));
            }

            ArrayList<Bitmap> imgList = imgs;
            final ArrayList<String> idList = ids;

            databaseAccess.close();

            final GridView gridView = (GridView) findViewById(R.id.gridview);
            gridView.setAdapter(new RecentTokensActivity.ImageAdapter(this, imgList));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Bitmap img = (Bitmap) parent.getItemAtPosition(position);
                    SelectedToken.img = img;
                    SelectedToken.id = idList.get(position);

                    MainActivity.mainact.finish();

                    Intent mainScreen = new Intent(RecentTokensActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("SETTOTAL", false);
                    mainScreen.putExtras(bundle);
                    startActivity(mainScreen);


                    finish();
                }
            });
        } else {
            TextView noRecent = (TextView) findViewById(R.id.text_noRecent);
            noRecent.setText("No recently used tokens to display.");
        }
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;
        ArrayList<Bitmap> imgs = new ArrayList<>();

        public ImageAdapter(Context c, ArrayList<Bitmap> imgs) {
            context = c;
            this.imgs = imgs;
        }

        public int getCount() {
            return imgs.size();
        }

        public Object getItem(int position) {
            return imgs.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        //---returns an ImageView view---
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(475, 665));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setPadding(1, 1, 1, 1);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageBitmap(imgs.get(position));
            return imageView;
        }
    }
}
