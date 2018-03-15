package com.github.cs449project;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Al on 3/7/2018.
 */

public class EffectsArrayAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Effect> list = new ArrayList<Effect>();
    private Context context;
    private EffectsFragment effectsFrag;

    public EffectsArrayAdapter(ArrayList<Effect> list, Context context, EffectsFragment effectsfrag) {
        this.context = context;
        this.list = list;
        this.effectsFrag = effectsfrag;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.partial_effects_list, null);
        }

        final Button incBtn = (Button) view.findViewById(R.id.button_inc);
        final Button decBtn = (Button) view.findViewById(R.id.button_dec);
        final Button allBtn = (Button) view.findViewById(R.id.button_setAll);
        final Button numBtn = (Button) view.findViewById(R.id.button_setNum);
        final Button deleteBtn = (Button) view.findViewById(R.id.button_delete);

        TextView abilities = (TextView) view.findViewById(R.id.text_abilities);
        String abilitiesStr = "";
        ArrayList<String> keywords = new ArrayList<>();
        keywords = list.get(position).getKeywords();
        for (int i = 0; i < keywords.size(); i++) {
            abilitiesStr += keywords.get(i) + ", ";
        }
        abilitiesStr = abilitiesStr.substring(0, abilitiesStr.length() - 2);
        abilities.setText(abilitiesStr);

        TextView numAffected = (TextView) view.findViewById(R.id.text_num);
        numAffected.setText(list.get(position).getNumAffected());
        if (list.get(position).getNumAffected() != "All") {
            incBtn.setVisibility(View.VISIBLE);
            decBtn.setVisibility(View.VISIBLE);
            allBtn.setVisibility(View.VISIBLE);
        }
        else {
            numBtn.setVisibility(View.VISIBLE);
        }

        if (list.get(position).getEffectDesc() != "N/A") {
            TextView desclabel = (TextView) view.findViewById(R.id.text_desclabel);
            desclabel.setVisibility(View.VISIBLE);
            TextView effectDesc = (TextView) view.findViewById(R.id.text_desc);
            effectDesc.setVisibility(View.VISIBLE);
            effectDesc.setText(list.get(position).getEffectDesc());
        }

        if (list.get(position).getSource() != "N/A") {
            TextView sourcelabel = (TextView) view.findViewById(R.id.text_srclabel);
            sourcelabel.setVisibility(View.VISIBLE);
            TextView source = (TextView) view.findViewById(R.id.text_src);
            source.setVisibility(View.VISIBLE);
            source.setText(list.get(position).getSource());
        }


        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
                effectsFrag.updateEffectsBtn(list.size());

            }
        });

        incBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                list.get(position).incNumAffected();
                notifyDataSetChanged();
            }
        });

        decBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                list.get(position).decNumAffected();
                notifyDataSetChanged();
            }
        });

        allBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                incBtn.setVisibility(View.GONE);
                decBtn.setVisibility(View.GONE);
                allBtn.setVisibility(View.GONE);
                numBtn.setVisibility(View.VISIBLE);
                list.get(position).allAffected();
                notifyDataSetChanged();
            }
        });

        numBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                incBtn.setVisibility(View.VISIBLE);
                decBtn.setVisibility(View.VISIBLE);
                allBtn.setVisibility(View.VISIBLE);
                numBtn.setVisibility(View.GONE);
                list.get(position).setNumAffected(1);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
