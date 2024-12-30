package com.example.projet;

import com.example.projet.dao.SingletonConnection;

import java.sql.Connection;

public class TestConnexion {
    public static void main(String[] args) {
        try {
            Connection connection = SingletonConnection.getConnection();
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connexion réussie !");
            } else {
                System.out.println("La connexion a échoué.");
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la tentative de connexion :");
            e.printStackTrace();
        }
    }
}
