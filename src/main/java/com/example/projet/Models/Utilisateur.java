package com.example.projet.Models;

public class Utilisateur {
    private int id;
    private String adresse;
    private String motDePasse;
    private String typeCompte;

    // Constructeur par défaut
    public Utilisateur() {}

    // Constructeur avec paramètres
    public Utilisateur(String adresse, String motDePasse, String typeCompte) {
        this.adresse = adresse;
        this.motDePasse = motDePasse;
        this.typeCompte = typeCompte;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getTypeCompte() {
        return typeCompte;
    }

    public void setTypeCompte(String typeCompte) {
        this.typeCompte = typeCompte;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", adresse='" + adresse + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", typeCompte='" + typeCompte + '\'' +
                '}';
    }
}
