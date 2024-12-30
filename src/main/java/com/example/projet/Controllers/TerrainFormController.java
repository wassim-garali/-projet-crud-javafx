package com.example.projet.Controllers;

import com.example.projet.Models.Terrain;
import com.example.projet.dao.TerrainDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TerrainFormController {

    @FXML
    private TextField nomField, typeField, prixField, villeField;

    private Terrain terrainToEdit = null;
    private TerrainDAO terrainDAO = new TerrainDAO();
    private String currentType = "";  // Définir ici la variable pour le type

    // Setter pour le terrain à modifier
    public void setTerrain(Terrain terrain, String type) {
        if (terrain != null) {
            // Si un terrain est passé, remplir les champs avec les données du terrain
            nomField.setText(terrain.getNom());
            typeField.setText(terrain.getType());
            prixField.setText(String.valueOf(terrain.getPrix()));
            villeField.setText(terrain.getVille());
        } else {
            // Si aucun terrain n'est passé (ajout), pré-remplir type avec currentType
            currentType = type; // Le type est passé depuis AdminPageController
            typeField.setText(currentType); // Remplir le champ type
        }
    }

    // Sauvegarder les informations du terrain (ajout ou modification)
    @FXML
    private void handleSaveButton(ActionEvent event) {
        if (nomField.getText().isEmpty() || typeField.getText().isEmpty() || prixField.getText().isEmpty() || villeField.getText().isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.", Alert.AlertType.WARNING);
            return;
        }

        String nom = nomField.getText();
        String type = typeField.getText();
        double prix = Double.parseDouble(prixField.getText());
        String ville = villeField.getText();

        if (terrainToEdit == null) {
            // Ajouter un nouveau terrain
            Terrain newTerrain = new Terrain(0, nom, type, prix, ville);
            if (terrainDAO.addTerrain(newTerrain)) {
                showAlert("Succès", "Terrain ajouté avec succès.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Erreur", "Impossible d'ajouter le terrain.", Alert.AlertType.ERROR);
            }
        } else {
            // Modifier un terrain existant
            terrainToEdit.setNom(nom);
            terrainToEdit.setType(type);
            terrainToEdit.setPrix(prix);
            terrainToEdit.setVille(ville);

            if (terrainDAO.updateTerrain(terrainToEdit)) {
                showAlert("Succès", "Terrain mis à jour avec succès.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Erreur", "Impossible de mettre à jour le terrain.", Alert.AlertType.ERROR);
            }
        }

        closeWindow();
    }

    // Annuler l'action et fermer la fenêtre
    @FXML
    private void handleCancelButton(ActionEvent event) {
        closeWindow();
    }

    // Fermer la fenêtre modale
    private void closeWindow() {
        Stage stage = (Stage) nomField.getScene().getWindow();
        stage.close();
    }

    // Afficher une alerte
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

