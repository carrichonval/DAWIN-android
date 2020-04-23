package com.example.mini_projet;

public class Ville {

    private int id_ville;
    private String nom;


    // Constructeur
    public Ville(int id,String nom) {
        this.id_ville=id;
        this.nom=nom;
    }

    public int getId_ville() {
        return id_ville;
    }
    public String getNom(){
        return nom;
    }


    public void setId_ville(int id_station) {
        this.id_ville = id_station;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}