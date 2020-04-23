package com.example.mini_projet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

class StationDAO {

    public static final String TABLE_NAME = "stations";
    public static final String KEY_ID_STATION="id_station";
    public static final String KEY_ADRESSE_STATION="adresse";
    public static final String VELO="velo";
    public static final String KEY_LATTITUDE_STATION="lattitude";
    public static final String KEY_LONGITUDE_STATION="lagitude";
    public static final String CREATE_TABLE_STATION = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_STATION+" INTEGER primary key," +
            " "+KEY_ADRESSE_STATION+" TEXT," +
            " "+VELO+" INTEGER," +
            " "+KEY_LATTITUDE_STATION+" DOUBLE," +
            " "+KEY_LONGITUDE_STATION+" DOUBLE" +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public StationDAO(Context context)
    {
        maBaseSQLite = MySQLite.getInstance(context);
    }

    public void open()
    {
        //on ouvre la table en lecture/écriture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close()
    {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addStation(Station station) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_ADRESSE_STATION, station.getAdresse_Station());
        values.put(VELO, station.getVelo());
        values.put(KEY_LATTITUDE_STATION, station.getLattitude());
        values.put(KEY_LONGITUDE_STATION, station.getLongitude());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int editStation(Station station) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_ADRESSE_STATION, station.getAdresse_Station());
        values.put(VELO, station.getVelo());
        values.put(KEY_LATTITUDE_STATION, station.getLattitude());
        values.put(KEY_LONGITUDE_STATION, station.getLongitude());

        String where = KEY_ID_STATION+" = ?";
        String[] whereArgs = {station.getId_station()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteStation(Station station) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_STATION+" = ?";
        String[] whereArgs = {station.getId_station()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Station getStation(int id) {
        // Retourne l'animal dont l'id est passé en paramètre

        Station s=new Station(0,"",0,0,0);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_STATION+"="+id, null);
        if (c.moveToFirst()) {
            s.setAdresse_station(c.getString(c.getColumnIndex(KEY_ADRESSE_STATION)));
            s.setVelo(c.getInt(c.getColumnIndex(VELO)));
            s.setLattitude(c.getDouble(c.getColumnIndex(KEY_LATTITUDE_STATION)));
            s.setLongitude(c.getDouble(c.getColumnIndex(KEY_LONGITUDE_STATION)));
            c.close();
        }

        return s;
    }

    public Cursor getStations() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

}