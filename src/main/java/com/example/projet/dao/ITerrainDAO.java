package com.example.projet.dao;

import com.example.projet.Models.Terrain;

import java.util.List;

public interface ITerrainDAO {
    boolean addTerrain(Terrain terrain); // Ajouter un terrain
    List<Terrain> getAllTerrains(); // Récupérer tous les terrains
    List<Terrain> getTerrainsByType(String type); // Récupérer les terrains par type
    boolean updateTerrain(Terrain terrain); // Mettre à jour un terrain
    boolean deleteTerrain(int id); // Supprimer un terrain
}
