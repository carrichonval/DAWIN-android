package com.example.mini_projet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

class VilleDAO {

    public static final String TABLE_NAME = "villes";
    public static final String KEY_ID_VILLE="id_ville";
    public static final String KEY_NOM_VILLE="nom";
    public static final String CREATE_TABLE_VILLE = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_VILLE+" INTEGER primary key," +
            " "+KEY_NOM_VILLE+" TEXT" +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public VilleDAO(Context context)
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

    public long addVille(Ville ville) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_NOM_VILLE, ville.getNom());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int editVille(Ville ville) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_NOM_VILLE, ville.getNom());

        String where = KEY_ID_VILLE+" = ?";
        String[] whereArgs = {ville.getId_ville()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteVille(Ville ville) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_VILLE+" = ?";
        String[] whereArgs = {ville.getId_ville()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Ville getVille(int id) {

        Ville a=new Ville(0,"");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_VILLE+"="+id, null);
        if (c.moveToFirst()) {
            a.setId_ville(c.getInt(c.getColumnIndex(KEY_ID_VILLE)));
            a.setNom(c.getString(c.getColumnIndex(KEY_NOM_VILLE)));
            c.close();
        }

        return a;
    }

    public Cursor getVilles() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

    public void dropVilles(){
        Log.d("VERIF","DROP VILLE");
        db.execSQL("DELETE FROM "+VilleDAO.TABLE_NAME+ ";");
    }

}