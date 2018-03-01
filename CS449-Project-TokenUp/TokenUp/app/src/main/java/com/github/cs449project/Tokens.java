package com.github.cs449project;

import java.util.ArrayList;

/**
 * Created by Al on 2/20/2018.
 */

public class Tokens {
    private int totalTokens = 0;
    private int attackers = 0;
    private int blockers = 0;
    private int readyTokens = 0;
    private int summoningSick = 0;
    private int tempSSick = 0;
    private int tapped = 0;
    private boolean playerTurn = true;



    public Tokens() {}

    public void setTotalTokens(int n){
        totalTokens = n;
    }

    public void setAttackers(int n){
        attackers = n;
    }

    public void setBlockers(int n){
        blockers = n;
    }

    public void setReadyTokens(int n){
        readyTokens = n;
    }

    public void setSummoningSick(int n){
        summoningSick = n;
    }

    public void setTapped(int n){
        tapped = n;
    }

    public int getTotalTokens() {
        return totalTokens;
    }

    public int getAttackers() {
        return attackers;
    }

    public int getBlockers() {
        return blockers;
    }

    public int getReadyTokens() {
        return readyTokens;
    }

    public int getSummoningSick() {
        return summoningSick;
    }

    public int getTapped() {
        return tapped;
    }

    public boolean getPlayerTurn() {
        return playerTurn;
    }

    public void incTotal() {
        if (SelectedToken.abilities.contains("Haste")) {
            readyTokens++;
        }
        else {
            summoningSick++;
        }
        totalTokens++;
    }

    public void incAttackers() {
        readyTokens--;
        attackers++;
    }

    public void incBlockers() {
        if (summoningSick > 0) {
            summoningSick--;
            tempSSick++;
        }
        else {
            readyTokens--;
        }
        blockers++;
    }

    public void incReady() {
        tapped--;
        readyTokens++;
    }

    public void incTapped() {
        readyTokens--;
        tapped++;
    }

    public void decTotal() {
        if (summoningSick > 0) {
            summoningSick--;
            totalTokens--;
        }
        else if (readyTokens > 0) {
            readyTokens--;
            totalTokens--;
        }
        else if (tapped > 0) {
            tapped--;
            totalTokens--;
        }
        else if (attackers > 0) {
            attackers--;
            totalTokens--;
        }
        else if (blockers > 0) {
            if (tempSSick > 0) {
                tempSSick--;
            }
            blockers--;
            totalTokens--;
        }
    }

    public void decAttackers() {
        attackers--;
        readyTokens++;
    }

    public void decBlockers() {
        if (tempSSick > 0) {
            blockers--;
            tempSSick--;
            summoningSick++;
        }
        else {
            blockers--;
            readyTokens++;
        }
    }



    public void opponentTurn() {
        playerTurn = false;

        if (!SelectedToken.abilities.contains("Viligance")) {
            tapped += attackers;
        }
        else {
            readyTokens += attackers;
        }
        attackers = 0;

        readyTokens += summoningSick;
        summoningSick = 0;
    }

    public void newUpkeep() {
        playerTurn = true;
        tempSSick = 0;

        readyTokens += tapped;
        readyTokens += blockers;
        readyTokens += summoningSick;
        tapped = 0;
        blockers = 0;
        summoningSick = 0;
    }
}
