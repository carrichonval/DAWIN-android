package com.example.mini_projet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "station.db";
    public static final String TABLE_VILLE = "villes_table";
    public static final String TABLE_STATIONS = "station_table";
    public static final String COL_1 = "id";
    public static final String COL_2 = "nom";

    public static final String COL_3 = "id";
    public static final String COL_4 = "adresse";
    public static final String COL_5 = "nbVelo";
    public static final String COL_6 = "lattitude";
    public static final String COL_7 = "longitude";

    public SQLiteDataBaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE table " + TABLE_VILLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NOM TEXT) ");
        db.execSQL("CREATE table " + TABLE_STATIONS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,ADRESSE TEXT,NMVELO INTEGER,LATTITUDE TEXT,LONGITUDE TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VILLE);
        onCreate(db);
    }

}
