package com.github.cs449project;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class EffectsFragment extends Fragment {

    private ArrayList<Effect> effects = new ArrayList<>();

    public EffectsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_effects, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        EffectsArrayAdapter adapter = new EffectsArrayAdapter(effects, getContext(), this);

        ListView lView = (ListView)getView().findViewById(R.id.view_effectlist);
        lView.setAdapter(adapter);

        if (effects.size() > 0) {
            Button effectsbtn = (Button) getActivity().findViewById(R.id.button_effects);
            effectsbtn.setText("Effects (" + Integer.toString(effects.size()) + ")");
        }

        final Button createEffectBtn = (Button) view.findViewById(R.id.button_create_new);
        createEffectBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent activityCreateEffect = new Intent(getActivity(), CreateEffectActivity.class);
                EffectsFragment.this.startActivity(activityCreateEffect);
            }
        });

        final Button hideBtn = (Button) view.findViewById(R.id.button_hide);
        hideBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                View effectsFragment = getActivity().findViewById(R.id.effectsLayout);
                effectsFragment.setVisibility(View.GONE);
            }
        });
    }

    public void addEffect(Effect effect) {
        effects.add(effect);

        EffectsArrayAdapter adapter = new EffectsArrayAdapter(effects, getContext(), this);

        ListView lView = (ListView)getView().findViewById(R.id.view_effectlist);
        lView.setAdapter(adapter);

        Button effectsbtn = (Button) getActivity().findViewById(R.id.button_effects);
        effectsbtn.setText("Effects (" + Integer.toString(effects.size()) + ")");
    }

    public void updateEffectsBtn(int effectsNum) {
        Button effectsbtn = (Button) getActivity().findViewById(R.id.button_effects);
        if (effectsNum > 0) {
            effectsbtn.setText("Effects (" + Integer.toString(effectsNum) + ")");
        }
        else {
            effectsbtn.setText("Effects");
        }
    }
}
