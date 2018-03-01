package com.github.cs449project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// http://www.java2s.com/Code/Android/UI/UsingGridViewtodisplayimages.htm reference

public class FilteredBrowseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_browse);

        Bundle bundle = getIntent().getExtras();
        String selection = bundle.getString("SELECTION");
        String filter = bundle.getString("FILTER");

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        ArrayList<Bitmap> imgList = databaseAccess.getImgs(selection, filter);
        final ArrayList<String> idList = databaseAccess.getIDs(selection, filter);

        databaseAccess.close();

        final GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this, imgList));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Bitmap img = (Bitmap) parent.getItemAtPosition(position);
                SelectedToken.img = img;
                SelectedToken.id = idList.get(0);


                ByFilterActivity.byFilter.finish();
                BrowseByActivity.browseBy.finish();
                MainActivity.mainact.finish();

                Intent mainScreen = new Intent(FilteredBrowseActivity.this, MainActivity.class);
                startActivity(mainScreen);


                finish();
            }
        });
    }

    public class ImageAdapter extends BaseAdapter
    {
        private Context context;
        ArrayList<Bitmap> imgs = new ArrayList<>();

        public ImageAdapter(Context c, ArrayList<Bitmap> imgs)
        {
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
        public View getView(int position, View convertView, ViewGroup parent)
        {
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
