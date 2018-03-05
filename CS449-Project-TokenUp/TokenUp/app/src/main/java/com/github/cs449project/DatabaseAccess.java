package com.github.cs449project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.sort;

/**
 * Created by Al on 2/7/2018.
 * Reference: http://www.javahelps.com/2015/04/import-and-use-external-database-in.html
 */

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;


    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }


    public void open() {
        this.database = openHelper.getWritableDatabase();
    }


    public void close() {
        if (database != null) {
            this.database.close();
        }
    }


    public List<String> getIds() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM Tokens", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        sort(list);
        return list;
    }

    public List<String> getNames() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT Name FROM Tokens ORDER BY Name", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(0);
            list.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getTypes() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT Type FROM Tokens ORDER BY Type", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String type = cursor.getString(0);
            list.add(type);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getSubTypes() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT SubType FROM Tokens ORDER BY SubType", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String subtype = cursor.getString(0);
            list.add(subtype);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getSets() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT MTGSet FROM Tokens ORDER BY MTGSet", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String set = cursor.getString(0);
            list.add(set);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getArtists() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT Artist FROM Tokens ORDER BY Artist", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String artist = cursor.getString(0);
            list.add(artist);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getColors() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT Colors FROM Tokens ORDER BY Colors", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String colorcombo = cursor.getString(0);
            String colors = "";

            char[] cols = colorcombo.toCharArray();
            Arrays.sort(cols);
            colorcombo = new String(cols);

            for (int i = 0; i < colorcombo.length(); i++){
                char col = colorcombo.charAt(i);
                switch(col) {
                    case 'R': colors += "Red/"; break;
                    case 'G': colors += "Green/"; break;
                    case 'B': colors += "Black/"; break;
                    case 'U': colors += "Blue/"; break;
                    case 'W': colors += "White/"; break;
                    case 'A': colors += "Artifact"; break;
                    case 'C': colors += "Colorless"; break;
                }
            }
            if (colors.charAt(colors.length()-1) == '/') {
                colors = colors.substring(0, colors.length()-1);
            }
            list.add(colors);
            cursor.moveToNext();
        }
        cursor.close();
        sort(list);
        return list;
    }

    public List<String> getTags() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT Tags FROM Tokens ORDER BY Tags", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String tags = cursor.getString(0);
            if (tags.length() > 0) {
                List<String> taglist = Arrays.asList(tags.split(","));
                for (int i = 0; i < taglist.size(); i++) {
                    list.add(taglist.get(i));
                }
            }
            cursor.moveToNext();
        }
        cursor.close();
        sort(list);
        return list;
    }




    public ArrayList<Bitmap> getImgs(String parameter, String category) {
        if (category.equals("colors")){
            return getImgsByColors(parameter);
        }
        if (category.equals("tag")){
            return getImgsByTag(parameter);
        }
        category = category.substring(0, 1).toUpperCase() + category.substring(1);
        if (category.equals("Set")){
            category = "MTGSet";
        }
        String[] param = new String[] { parameter };
        ArrayList<Bitmap> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT ImgFile FROM Tokens WHERE " + category + " = ? ORDER BY Name, Artist, MTGSet", param);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            byte[] byteArray = cursor.getBlob(0);
            Bitmap img = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            list.add(img);;
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }

    public ArrayList<Bitmap> getImgsByColors(String colors) {
        ArrayList<Bitmap> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT ImgFile, Colors FROM Tokens ORDER BY Name, Artist, MTGSet", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String currentcombo = cursor.getString(1);
            String currentcolors = "";

            for (int i = 0; i < currentcombo.length(); i++){
                char col = currentcombo.charAt(i);
                switch(col) {
                    case 'R': currentcolors += "Red/"; break;
                    case 'G': currentcolors += "Green/"; break;
                    case 'B': currentcolors += "Black/"; break;
                    case 'U': currentcolors += "Blue/"; break;
                    case 'W': currentcolors += "White/"; break;
                    case 'A': currentcolors += "Artifact"; break;
                    case 'C': currentcolors += "Colorless"; break;
                }
            }
            if (currentcolors.charAt(currentcolors.length()-1) == '/') {
                currentcolors = currentcolors.substring(0, currentcolors.length()-1);
            }

            if (colors.equals(currentcolors)) {
                byte[] byteArray = cursor.getBlob(0);
                Bitmap img = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                list.add(img);
            }

            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<Bitmap> getImgsByTag(String tag) {
        ArrayList<Bitmap> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT ImgFile, Tags FROM Tokens ORDER BY Name, Artist, MTGSet", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String currenttags = cursor.getString(1);
            List<String> currenttaglist = Arrays.asList(currenttags.split(","));
            if (currenttaglist.contains(tag)) {
                byte[] byteArray = cursor.getBlob(0);
                Bitmap img = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                list.add(img);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }


    public ArrayList<String> getIDs(String parameter, String category) {
        if (category.equals("colors")){
            return getIDsByColors(parameter);
        }
        if (category.equals("tag")){
            return getIDsByTag(parameter);
        }
        category = category.substring(0, 1).toUpperCase() + category.substring(1);
        if (category.equals("Set")){
            category = "MTGSet";
        }
        String[] param = new String[] { parameter };
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Id FROM Tokens WHERE " + category + " = ? ORDER BY Name, Artist, MTGSet", param);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));;
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }

    public ArrayList<String> getIDsByColors(String colors) {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Id, Colors FROM Tokens ORDER BY Name, Artist, MTGSet", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String currentcombo = cursor.getString(1);
            String currentcolors = "";

            for (int i = 0; i < currentcombo.length(); i++){
                char col = currentcombo.charAt(i);
                switch(col) {
                    case 'R': currentcolors += "Red/"; break;
                    case 'G': currentcolors += "Green/"; break;
                    case 'B': currentcolors += "Black/"; break;
                    case 'U': currentcolors += "Blue/"; break;
                    case 'W': currentcolors += "White/"; break;
                    case 'A': currentcolors += "Artifact"; break;
                    case 'C': currentcolors += "Colorless"; break;
                }
            }
            if (currentcolors.charAt(currentcolors.length()-1) == '/') {
                currentcolors = currentcolors.substring(0, currentcolors.length()-1);
            }

            if (colors.equals(currentcolors)) {
                list.add(cursor.getString(0));
            }

            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<String> getIDsByTag(String tag) {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Id, Tags FROM Tokens ORDER BY Name, Artist, MTGSet", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String currenttags = cursor.getString(1);
            List<String> currenttaglist = Arrays.asList(currenttags.split(","));
            if (currenttaglist.contains(tag)) {
                list.add(cursor.getString(0));
            }
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getAbilities(String id) {
        List<String> list = new ArrayList<>();
        String[] param = new String[] { id };
        Cursor cursor = database.rawQuery("SELECT Abilities FROM Tokens WHERE Id = ?", param);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String abilities = cursor.getString(0);
            list = Arrays.asList(abilities.split("\n"));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

}
