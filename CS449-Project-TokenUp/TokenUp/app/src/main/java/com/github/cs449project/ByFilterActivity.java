package com.github.cs449project;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ByFilterActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_filter);

        Bundle bundle = getIntent().getExtras();
        final String filter = bundle.getString("FILTER");

        this.listView = (ListView) findViewById(R.id.listView);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<String> byFilter = new ArrayList<>();

        if (filter.equals("name")) {
            byFilter = databaseAccess.getNames();
        }

        if (filter.equals("type")) {
            byFilter = databaseAccess.getTypes();
        }

        if (filter.equals("subtype")) {
            byFilter = databaseAccess.getSubTypes();
        }

        if (filter.equals("set")) {
            byFilter = databaseAccess.getSets();
        }

        if (filter.equals("artist")) {
            byFilter = databaseAccess.getArtists();
        }

        if (filter.equals("colors")) {
            byFilter = databaseAccess.getColors();
        }

        if (filter.equals("tag")) {
            byFilter = databaseAccess.getTags();
        }

        databaseAccess.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, byFilter);
        this.listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String)listView.getItemAtPosition(position);
                Intent activityBrowseSelect = new Intent(ByFilterActivity.this, FilteredBrowseActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("SELECTION", selection);
                bundle.putString("FILTER", filter);
                activityBrowseSelect.putExtras(bundle);

                startActivity(activityBrowseSelect);

                finish();
            }
        });
    }



}
