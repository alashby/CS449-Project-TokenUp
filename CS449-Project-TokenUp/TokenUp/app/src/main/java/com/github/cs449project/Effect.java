package com.github.cs449project;

import java.util.ArrayList;

/**
 * Created by Al on 3/6/2018.
 */

public class Effect {
    private ArrayList<String> keywords;
    private String effectDesc;
    private int numAffected;
    private String source;

    public Effect(ArrayList<String> kwords, int num, String desc, String src) {
        keywords = kwords;
        effectDesc = desc;
        numAffected = num;
        source = src;
    }

    public ArrayList<String> getKeywords() { return keywords; }

    public String getNumAffected() {
        if (numAffected > 0) {
            return Integer.toString(numAffected);
        }
        else {
            return "All";
        }
    }

    public String getEffectDesc() {
        return effectDesc;
    }

    public String getSource() {
        return source;
    }

    public void incNumAffected() { numAffected++; }

    public void decNumAffected() {
        if (numAffected > 1) {
            numAffected--;
        }
    }

    public void allAffected() { numAffected = 0;}

    public void setNumAffected(int num) { numAffected = num; }
}
