package com.github.cs449project;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BrowseByActivity extends AppCompatActivity {

    public static Activity browseBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_by);
        browseBy = this;
    }

    public void search(View view) {
        Intent activitySearch = new Intent(BrowseByActivity.this, SearchActivity.class);
        startActivity(activitySearch);
    }

    public void browseByName(View view) {
        Intent activityByName = new Intent(BrowseByActivity.this, ByFilterActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("FILTER", "name");
        activityByName.putExtras(bundle);

        startActivity(activityByName);
    }

    public void browseByType(View view) {
        Intent activityByType = new Intent(BrowseByActivity.this, ByFilterActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("FILTER", "type");
        activityByType.putExtras(bundle);

        startActivity(activityByType);
    }

    public void browseBySubType(View view) {
        Intent activityBySubType = new Intent(BrowseByActivity.this, ByFilterActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("FILTER", "subtype");
        activityBySubType.putExtras(bundle);

        startActivity(activityBySubType);
    }

    public void browseBySet(View view) {
        Intent activityBySet = new Intent(BrowseByActivity.this, ByFilterActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("FILTER", "set");
        activityBySet.putExtras(bundle);

        startActivity(activityBySet);
    }

    public void browseByArtist(View view) {
        Intent activityByArtist = new Intent(BrowseByActivity.this, ByFilterActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("FILTER", "artist");
        activityByArtist.putExtras(bundle);

        startActivity(activityByArtist);
    }

    public void browseByColors(View view) {
        Intent activityByColors = new Intent(BrowseByActivity.this, ByFilterActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("FILTER", "colors");
        activityByColors.putExtras(bundle);

        startActivity(activityByColors);
    }

    public void browseByTag(View view) {
        Intent activityByTags = new Intent(BrowseByActivity.this, ByFilterActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("FILTER", "tag");
        activityByTags.putExtras(bundle);

        startActivity(activityByTags);
    }


}
