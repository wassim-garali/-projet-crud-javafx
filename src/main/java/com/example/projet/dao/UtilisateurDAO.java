package com.example.projet.dao;

import com.example.projet.Models.Utilisateur;
import java.sql.*;

public class UtilisateurDAO {

    private Connection connection;

    public UtilisateurDAO() {
        // Utilisation de la méthode getConnection() pour obtenir la connexion
        connection = SingletonConnection.getConnection();
    }

    // Méthode pour vérifier un utilisateur
    public Utilisateur verifierUtilisateur(String adresse, String mdp) {
        String sql = "SELECT * FROM utilisateurs WHERE adresse = ? AND mot_de_passe = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, adresse);
            statement.setString(2, mdp);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setId(resultSet.getInt("id"));
                    utilisateur.setAdresse(resultSet.getString("adresse"));
                    utilisateur.setMotDePasse(resultSet.getString("mot_de_passe"));
                    utilisateur.setTypeCompte(resultSet.getString("type_compte"));
                    return utilisateur;  // L'utilisateur est trouvé, retourne l'objet Utilisateur
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Vous pouvez ici ajouter une gestion plus appropriée des exceptions
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestion de l'exception de la requête SQL
        }
        return null;  // L'utilisateur n'est pas trouvé
    }
}
