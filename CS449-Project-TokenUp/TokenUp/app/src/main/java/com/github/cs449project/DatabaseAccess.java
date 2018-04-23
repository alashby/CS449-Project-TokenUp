package com.github.cs449project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
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

    private String formatStr(String str) {
        str = str.toUpperCase();

        if (str.contains("%")) {
            str = str.replaceAll("%", "\\%");
        }
        if (str.contains("'")) {
            str = str.replaceAll("'", "\\'");
        }
        if (str.contains("\"")) {
            str = str.replaceAll("\"", "\\\"");
        }
        if (str.contains("_")) {
            str = str.replaceAll("_", "\\_");
        }

        return str;
    }

   public ArrayList<Bitmap> queryImgs(Bundle bundle) {
        ArrayList<Bitmap> list = new ArrayList<>();
        String whereClause = "WHERE ";

        if (bundle.containsKey("input_id")) {
            whereClause += "upper(Id) LIKE '%" + formatStr(bundle.getString("input_id")) + "%' ESCAPE '\\' AND ";
        }
        if (bundle.containsKey("input_name")) {
            whereClause += "upper(Name) LIKE '%" + formatStr(bundle.getString("input_name")) + "%' ESCAPE '\\' AND ";
        }
        if (bundle.containsKey("input_type")) {
            whereClause += "upper(Type) LIKE '%" + formatStr(bundle.getString("input_type")) + "%' ESCAPE '\\' AND ";
        }
        if (bundle.containsKey("input_subtype")) {
            whereClause += "upper(SubType) LIKE '%" + formatStr(bundle.getString("input_subtype")) + "%' ESCAPE '\\' AND ";
        }
        if (bundle.containsKey("input_set")) {
            whereClause += "upper(MTGSet) LIKE '%" + formatStr(bundle.getString("input_set")) + "%' ESCAPE '\\' AND ";
        }
        if (bundle.containsKey("text_power")) {
            whereClause += "Power = '" + bundle.getString("text_power") + "' AND ";
        }
        if (bundle.containsKey("text_toughness")) {
            whereClause += "Toughness = '" + bundle.getString("text_toughness") + "' AND ";
        }
        if (bundle.containsKey("input_artist")) {
            whereClause += "upper(Artist) LIKE '%" + formatStr(bundle.getString("input_artist") + "%' ESCAPE '\\' AND ");
        }
        if (bundle.containsKey("input_abilities")) {
            for (int i = 0; i < bundle.getStringArray("input_abilities").length; i++) {
                whereClause += "upper(Abilities) LIKE '%" + bundle.getStringArray("input_abilities")[i] + "%' ESCAPE '\\' AND ";
            }
        }
        if (bundle.containsKey("input_tags")) {
            for (int i = 0; i < bundle.getStringArray("input_tags").length; i++) {
                whereClause += "upper(Tags) LIKE '%" + bundle.getStringArray("input_tags")[i] + "%' ESCAPE '\\' AND ";
            }
        }
        if (bundle.containsKey("button_monocolor")) {
            whereClause += "(";
            if (bundle.containsKey("BLACK")) {
                whereClause += "Colors = 'B' OR ";
            }
            if (bundle.containsKey("BLUE")) {
                whereClause += "Colors = 'U' OR ";
            }
            if (bundle.containsKey("GREEN")) {
                whereClause += "Colors = 'G' OR ";
            }
            if (bundle.containsKey("RED")) {
                whereClause += "Colors = 'R' OR ";
            }
            if (bundle.containsKey("WHITE")) {
                whereClause += "Colors = 'W' OR ";
            }
            if (bundle.containsKey("ARTIFACT")) {
                whereClause += "Colors = 'A' OR ";
            }
            if (bundle.containsKey("COLORLESS")) {
                whereClause += "Colors = 'C' OR ";
            }
            whereClause = whereClause.substring(0, whereClause.length()-4);
            whereClause += ") AND ";
        }
        if (bundle.containsKey("button_multicolor")) {
            whereClause += "(";
            if (bundle.containsKey("BLACK")) {
                whereClause += "Colors LIKE '%B%' AND ";
            }
            if (bundle.containsKey("BLUE")) {
                whereClause += "Colors LIKE '%U%' AND ";
            }
            if (bundle.containsKey("GREEN")) {
                whereClause += "Colors LIKE '%G%' AND ";
            }
            if (bundle.containsKey("RED")) {
                whereClause += "Colors LIKE '%R%' AND ";;
            }
            if (bundle.containsKey("WHITE")) {
                whereClause += "Colors LIKE '%W%' AND ";
            }
            if (bundle.containsKey("ARTIFACT")) {
                whereClause += "Colors LIKE '%A%' AND ";
            }
            if (bundle.containsKey("COLORLESS")) {
                whereClause += "Colors LIKE '%C%' AND ";
            }
            whereClause = whereClause.substring(0, whereClause.length()-5);
            whereClause += ") AND ";
        }
       if (bundle.containsKey("button_bothcolor")) {
           whereClause += "(";
           if (bundle.containsKey("BLACK")) {
               whereClause += "Colors LIKE '%B%' OR ";
           }
           if (bundle.containsKey("BLUE")) {
               whereClause += "Colors LIKE '%U%' OR ";
           }
           if (bundle.containsKey("GREEN")) {
               whereClause += "Colors LIKE '%G%' OR ";
           }
           if (bundle.containsKey("RED")) {
               whereClause += "Colors LIKE '%R%' OR ";;
           }
           if (bundle.containsKey("WHITE")) {
               whereClause += "Colors LIKE '%W%' OR ";
           }
           if (bundle.containsKey("ARTIFACT")) {
               whereClause += "Colors LIKE '%A%' OR ";
           }
           if (bundle.containsKey("COLORLESS")) {
               whereClause += "Colors LIKE '%C%' OR ";
           }
           whereClause = whereClause.substring(0, whereClause.length()-4);
           whereClause += ") AND ";
        }

        whereClause = whereClause.substring(0, whereClause.length()-5);

        Cursor cursor = database.rawQuery("SELECT ImgFile FROM Tokens " + whereClause, null);
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

    public ArrayList<String> getIDs(Bundle bundle) {
        ArrayList<String> list = new ArrayList<>();
        String whereClause = "WHERE ";

        if (bundle.containsKey("input_id")) {
            whereClause += "upper(Id) LIKE '%" + formatStr(bundle.getString("input_id")) + "%' ESCAPE '\\' AND ";
        }
        if (bundle.containsKey("input_name")) {
            whereClause += "upper(Name) LIKE '%" + formatStr(bundle.getString("input_name")) + "%' ESCAPE '\\' AND ";
        }
        if (bundle.containsKey("input_type")) {
            whereClause += "upper(Type) LIKE '%" + formatStr(bundle.getString("input_type")) + "%' ESCAPE '\\' AND ";
        }
        if (bundle.containsKey("input_subtype")) {
            whereClause += "upper(SubType) LIKE '%" + formatStr(bundle.getString("input_subtype")) + "%' ESCAPE '\\' AND ";
        }
        if (bundle.containsKey("input_set")) {
            whereClause += "upper(MTGSet) LIKE '%" + formatStr(bundle.getString("input_set")) + "%' ESCAPE '\\' AND ";
        }
        if (bundle.containsKey("text_power")) {
            whereClause += "Power = '" + bundle.getString("text_power") + "' AND ";
        }
        if (bundle.containsKey("text_toughness")) {
            whereClause += "Toughness = '" + bundle.getString("text_toughness") + "' AND ";
        }
        if (bundle.containsKey("input_artist")) {
            whereClause += "upper(Artist) LIKE '%" + formatStr(bundle.getString("input_artist") + "%' ESCAPE '\\' AND ");
        }
        if (bundle.containsKey("input_abilities")) {
            for (int i = 0; i < bundle.getStringArray("input_abilities").length; i++) {
                whereClause += "upper(Abilities) LIKE '%" + bundle.getStringArray("input_abilities")[i] + "%' ESCAPE '\\' AND ";
            }
        }
        if (bundle.containsKey("input_tags")) {
            for (int i = 0; i < bundle.getStringArray("input_tags").length; i++) {
                whereClause += "upper(Tags) LIKE '%" + bundle.getStringArray("input_tags")[i] + "%' ESCAPE '\\' AND ";
            }
        }
        if (bundle.containsKey("button_monocolor")) {
            whereClause += "(";
            if (bundle.containsKey("BLACK")) {
                whereClause += "Colors = 'B' OR ";
            }
            if (bundle.containsKey("BLUE")) {
                whereClause += "Colors = 'U' OR ";
            }
            if (bundle.containsKey("GREEN")) {
                whereClause += "Colors = 'G' OR ";
            }
            if (bundle.containsKey("RED")) {
                whereClause += "Colors = 'R' OR ";
            }
            if (bundle.containsKey("WHITE")) {
                whereClause += "Colors = 'W' OR ";
            }
            if (bundle.containsKey("ARTIFACT")) {
                whereClause += "Colors = 'A' OR ";
            }
            if (bundle.containsKey("COLORLESS")) {
                whereClause += "Colors = 'C' OR ";
            }
            whereClause = whereClause.substring(0, whereClause.length()-4);
            whereClause += ") AND ";
        }
        if (bundle.containsKey("button_multicolor")) {
            whereClause += "(";
            if (bundle.containsKey("BLACK")) {
                whereClause += "Colors LIKE '%B%' AND ";
            }
            if (bundle.containsKey("BLUE")) {
                whereClause += "Colors LIKE '%U%' AND ";
            }
            if (bundle.containsKey("GREEN")) {
                whereClause += "Colors LIKE '%G%' AND ";
            }
            if (bundle.containsKey("RED")) {
                whereClause += "Colors LIKE '%R%' AND ";;
            }
            if (bundle.containsKey("WHITE")) {
                whereClause += "Colors LIKE '%W%' AND ";
            }
            if (bundle.containsKey("ARTIFACT")) {
                whereClause += "Colors LIKE '%A%' AND ";
            }
            if (bundle.containsKey("COLORLESS")) {
                whereClause += "Colors LIKE '%C%' AND ";
            }
            whereClause = whereClause.substring(0, whereClause.length()-5);
            whereClause += ") AND ";
        }
        if (bundle.containsKey("button_bothcolor")) {
            whereClause += "(";
            if (bundle.containsKey("BLACK")) {
                whereClause += "Colors LIKE '%B%' OR ";
            }
            if (bundle.containsKey("BLUE")) {
                whereClause += "Colors LIKE '%U%' OR ";
            }
            if (bundle.containsKey("GREEN")) {
                whereClause += "Colors LIKE '%G%' OR ";
            }
            if (bundle.containsKey("RED")) {
                whereClause += "Colors LIKE '%R%' OR ";;
            }
            if (bundle.containsKey("WHITE")) {
                whereClause += "Colors LIKE '%W%' OR ";
            }
            if (bundle.containsKey("ARTIFACT")) {
                whereClause += "Colors LIKE '%A%' OR ";
            }
            if (bundle.containsKey("COLORLESS")) {
                whereClause += "Colors LIKE '%C%' OR ";
            }
            whereClause = whereClause.substring(0, whereClause.length()-4);
            whereClause += ") AND ";
        }

        whereClause = whereClause.substring(0, whereClause.length()-5);

        Cursor cursor = database.rawQuery("SELECT Id FROM Tokens " + whereClause, null);
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

    public void createToken(String id, String name, byte[] img, String type, String subtype,
                            String mtgset, ArrayList<String> colors, String power, String toughness,
                            String[] abilities, String artist, String[] tags) {

        String colorsstr = "";
        sort(colors);
        for (int i = 0; i < colors.size(); i++) {
            colorsstr += colors.get(i);
        }
        String abilitiesstr = "";
        for (int i = 0; i < abilities.length; i++) {
            abilitiesstr += abilities[i].trim() + "\n";
        }
        String tagsstr = "";
        for (int i = 0; i < tags.length; i++) {
            tagsstr += tags[i].trim() + ",";
        }

        String createStatement = "INSERT INTO Tokens(Id,Name,ImgFile,Type,SubType,MTGSet,Colors,Power,Toughness,Abilities,Artist,Tags)VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
        SQLiteStatement stmt = database.compileStatement(createStatement);
        stmt.bindString(1,id);
        stmt.bindString(2,name);
        stmt.bindBlob(3,img);
        stmt.bindString(4,type);
        stmt.bindString(5,subtype);
        stmt.bindString(6,mtgset);
        stmt.bindString(7,colorsstr);
        if (power.isEmpty()) {
            stmt.bindNull(8);
        }
        else {
            stmt.bindString(8,power);
        }
        if (toughness.isEmpty()) {
            stmt.bindNull(9);
        }
        else {
            stmt.bindString(9,toughness);
        }
        stmt.bindString(10,abilitiesstr);
        stmt.bindString(11,artist.trim());
        stmt.bindString(12,tagsstr);

        stmt.executeInsert();
    }

}
