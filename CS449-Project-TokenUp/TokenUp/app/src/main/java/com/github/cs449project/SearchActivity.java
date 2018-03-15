package com.github.cs449project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Spinner setspin = (Spinner) findViewById(R.id.spinner_set);
        ArrayAdapter<CharSequence> arrAdapter = ArrayAdapter.createFromResource(this, R.array.sets_array, android.R.layout.simple_spinner_item);
        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setspin.setAdapter(arrAdapter);

        ArrayAdapter<CharSequence> optAdapter = ArrayAdapter.createFromResource(this, R.array.search_array, android.R.layout.simple_spinner_item);
        optAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner idoptspin = (Spinner) findViewById(R.id.spinner_idoptions);
        idoptspin.setAdapter(optAdapter);

        Spinner nameoptspin = (Spinner) findViewById(R.id.spinner_nameoptions);
        nameoptspin.setAdapter(optAdapter);

        Spinner typeoptspin = (Spinner) findViewById(R.id.spinner_typeoptions);
        typeoptspin.setAdapter(optAdapter);

        Spinner subtypeoptspin = (Spinner) findViewById(R.id.spinner_subtypeoptions);
        subtypeoptspin.setAdapter(optAdapter);

        Spinner setoptspin = (Spinner) findViewById(R.id.spinner_setoptions);
        setoptspin.setAdapter(optAdapter);

        Spinner coloroptspin = (Spinner) findViewById(R.id.spinner_coloroptions);
        coloroptspin.setAdapter(optAdapter);

        Spinner ptoptspin = (Spinner) findViewById(R.id.spinner_ptoptions);
        ptoptspin.setAdapter(optAdapter);

        Spinner abilitiesoptspin = (Spinner) findViewById(R.id.spinner_abilitiesoptions);
        abilitiesoptspin.setAdapter(optAdapter);

        Spinner artistoptspin = (Spinner) findViewById(R.id.spinner_artistoptions);
        artistoptspin.setAdapter(optAdapter);

        Spinner tagsoptspin = (Spinner) findViewById(R.id.spinner_tagsoptions);
        tagsoptspin.setAdapter(optAdapter);

        setspin.setAdapter(arrAdapter);
    }
}
