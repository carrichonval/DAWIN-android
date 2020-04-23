package com.example.mini_projet;

public class Station {

    private int id_station;
    private String adresse_station;
    private Integer velo;
    private Double lattitude;
    private Double longitude;

    // Constructeur
    public Station(int id,String adresse,int velo,double lattitude, double longitude) {
        this.id_station=id;
        this.adresse_station=adresse;
        this.velo=velo;
        this.lattitude=lattitude;
        this.longitude=longitude;
    }

    public int getId_station() {
        return id_station;
    }
    public String getAdresse_Station() {
        return adresse_station;
    }
    public int getVelo() {
        return velo;
    }
    public double getLattitude() {
        return lattitude;
    }
    public double getLongitude() {
        return longitude;
    }

    public void setId_station(int id_station) {
        this.id_station = id_station;
    }

    public void setAdresse_station(String nom) {
        this.adresse_station = nom;
    }

    public void setVelo(int velo) {
        this.velo = velo;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }



}