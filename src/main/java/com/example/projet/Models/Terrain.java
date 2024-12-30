package com.example.projet.Models;

import javafx.beans.property.*;

public class Terrain {

    private IntegerProperty id;
    private StringProperty nom;
    private StringProperty type;
    private DoubleProperty prix;
    private StringProperty ville;

    // Constructeur
    public Terrain(int id, String nom, String type, double prix, String ville) {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.type = new SimpleStringProperty(type);
        this.prix = new SimpleDoubleProperty(prix);
        this.ville = new SimpleStringProperty(ville);
    }


    // Getters et setters
    public StringProperty nomProperty() {
        return nom;
    }

    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public DoubleProperty prixProperty() {
        return prix;
    }

    public double getPrix() {
        return prix.get();
    }

    public void setPrix(double prix) {
        this.prix.set(prix);
    }

    public StringProperty villeProperty() {
        return ville;
    }

    public String getVille() {
        return ville.get();
    }

    public void setVille(String ville) {
        this.ville.set(ville);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }
}
