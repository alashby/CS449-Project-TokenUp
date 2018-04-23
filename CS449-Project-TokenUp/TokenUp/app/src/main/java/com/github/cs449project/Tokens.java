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
        Boolean haste = false;
        for (int i = 0; i < SelectedToken.abilities.size(); i++) {
            if (SelectedToken.abilities.get(i).toUpperCase().equals("HASTE")) {
                haste = true;
            }
        }
        if (haste) {
            readyTokens++;
        }
        else {
            summoningSick++;
        }
        totalTokens++;
    }

    public void incAttackers() {
        if (playerTurn && readyTokens > 0) {
            readyTokens--;
            attackers++;
        }
    }

    public void incBlockers() {
        if (!playerTurn && summoningSick > 0) {
            summoningSick--;
            tempSSick++;
            blockers++;
        }
        else if (!playerTurn && readyTokens > 0) {
            readyTokens--;
            blockers++;
        }
    }

    public void incReady() {
        if (tapped > 0) {
            tapped--;
            readyTokens++;
        }
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
        if (attackers > 0) {
            attackers--;
            readyTokens++;
        }
    }

    public void decBlockers() {
        if (tempSSick > 0) {
            blockers--;
            tempSSick--;
            summoningSick++;
        }
        else if (blockers > 0){
            blockers--;
            readyTokens++;
        }
    }

    public void kill(String tokenState) {
        switch(tokenState) {
            case "attackers":
                if (attackers > 0) { attackers--; totalTokens--; } break;
            case "blockers":
                if (blockers > 0) { blockers--; totalTokens--; } break;
            case "ready":
                if (readyTokens > 0) { readyTokens--; totalTokens--; } break;
            case "ssick":
                if (summoningSick > 0) { summoningSick--; totalTokens--; } break;
            case "tapped":
                if (tapped > 0) { tapped--; totalTokens--; } break;
        }
    }

    public void tap(String tokenState) {
        switch (tokenState) {
            case "attackers":
                if (attackers > 0) { attackers--; tapped++; } break;
            case "blockers":
                if (blockers > 0) { blockers--; tapped++; } break;
            case "ready":
                if (readyTokens > 0) { readyTokens--; tapped++; } break;
        }
    }

    public void opponentTurn() {
        playerTurn = false;

        Boolean vigilance = false;
        for (int i = 0; i < SelectedToken.abilities.size(); i++) {
            if (SelectedToken.abilities.get(i).toUpperCase().equals("VIGILANCE")) {
                vigilance = true;
            }
        }
        if (vigilance) {
            readyTokens += attackers;

        }
        else {
            tapped += attackers;
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
