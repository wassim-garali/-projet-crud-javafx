package com.example.projet.dao;

import com.example.projet.Models.Terrain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TerrainDAO implements ITerrainDAO {

    @Override
    public boolean addTerrain(Terrain terrain) {
        String query = "INSERT INTO terrains (nom, type, prix, ville) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, terrain.getNom());
            ps.setString(2, terrain.getType());
            ps.setDouble(3, terrain.getPrix());
            ps.setString(4, terrain.getVille());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        terrain.setId(generatedKeys.getInt(1)); // Attribuer l'ID généré
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Terrain> getAllTerrains() {
        List<Terrain> terrains = new ArrayList<>();
        String query = "SELECT * FROM terrains";
        try (Connection connection = SingletonConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Terrain terrain = new Terrain(
                        rs.getInt("id_terrain"),
                        rs.getString("nom"),
                        rs.getString("type"),
                        rs.getDouble("prix"),
                        rs.getString("ville")
                );
                terrains.add(terrain);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return terrains;
    }

    @Override
    public List<Terrain> getTerrainsByType(String type) {
        List<Terrain> terrains = new ArrayList<>();
        String query = "SELECT * FROM terrains WHERE type = ?";
        try (PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(query)) {

            ps.setString(1, type);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Terrain terrain = new Terrain(
                            rs.getInt("id_terrain"),
                            rs.getString("nom"),
                            rs.getString("type"),
                            rs.getDouble("prix"),
                            rs.getString("ville")
                    );
                    terrains.add(terrain);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return terrains;
    }

    @Override
    public boolean updateTerrain(Terrain terrain) {
        String query = "UPDATE terrains SET nom = ?, type = ?, prix = ?, ville = ? WHERE id_terrain = ?";
        try (PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(query)) {

            ps.setString(1, terrain.getNom());
            ps.setString(2, terrain.getType());
            ps.setDouble(3, terrain.getPrix());
            ps.setString(4, terrain.getVille());
            ps.setInt(5, terrain.getId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteTerrain(int id) {
        String query = "DELETE FROM terrains WHERE id_terrain = ?";
        try (PreparedStatement ps = SingletonConnection.getConnection().prepareStatement(query)) {

            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
