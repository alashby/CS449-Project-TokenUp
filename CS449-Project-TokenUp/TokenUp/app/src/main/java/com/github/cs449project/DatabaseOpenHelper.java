package com.github.cs449project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
/**
 * Created by Al on 2/7/2018.
 * Reference: http://www.javahelps.com/2015/04/import-and-use-external-database-in.html
 */

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "tokens.db";
    private static final int DATABASE_VERSION = 7;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
