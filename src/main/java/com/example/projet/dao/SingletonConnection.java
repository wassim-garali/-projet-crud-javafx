package com.example.projet.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnection {
    // Instance unique de connexion
    private static Connection connection;

    // Bloc static pour initialiser la connexion
    static {
        try {
            // Charger le driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établir la connexion à la base de données
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/location_terrains?useSSL=false&serverTimezone=UTC", // URL de la base
                    "root", // Nom d'utilisateur
                    "" // Mot de passe
            );
            System.out.println("Connexion à la base de données réussie !");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC introuvable !");
            e.printStackTrace();
            throw new RuntimeException("Erreur : Driver JDBC introuvable.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données.");
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la connexion à la base de données.");
        }
    }

    // Constructeur privé pour empêcher l'instanciation de la classe
    private SingletonConnection() {}

    // Méthode pour obtenir la connexion
    public static Connection getConnection() {
        try {
            // Vérifier si la connexion est déjà fermée ou nulle
            if (connection == null || connection.isClosed()) {
                throw new SQLException("La connexion à la base de données est fermée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Si la connexion est fermée ou nulle, tenter de la réinitialiser
            try {
                // Essayer de rétablir la connexion
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/location_terrains?useSSL=false&serverTimezone=UTC",
                        "root",
                        ""
                );
                System.out.println("Nouvelle connexion établie.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new RuntimeException("Impossible de rétablir la connexion.");
            }
        }
        return connection;
    }

    // Optionnel : méthode pour fermer la connexion
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connexion fermée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
