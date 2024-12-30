package com.example.projet.Controllers;

import com.example.projet.Models.Terrain;
import com.example.projet.dao.TerrainDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;

public class AdminPageController {

    public ImageView userIcon;
    @FXML private Button homeButton, footButton, basketButton, tennisButton, deleteButton;
    @FXML private Button addButton, updateButton;
    @FXML private TableView<Terrain> terrainTable;
    @FXML private TableColumn<Terrain, String> nomColumn;
    @FXML private TableColumn<Terrain, String> typeColumn;
    @FXML private TableColumn<Terrain, Double> prixColumn;
    @FXML private TableColumn<Terrain, String> villeColumn;
    @FXML private Label usernameLabel;
    @FXML private VBox contentArea;
    @FXML private HBox menuArea;
    private String currentType = "";

    private TerrainDAO terrainDAO = new TerrainDAO();

    @FXML
    public void initialize() {
        // Initialiser les colonnes de la TableView avec les propriétés de l'entité Terrain
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        villeColumn.setCellValueFactory(new PropertyValueFactory<>("ville"));

        // Afficher tous les terrains par défaut lorsque la page est chargée
        afficherTerrains();
        disableCRUDButtons();
    }

    @FXML
    private void handleHomeButton() {
        afficherTerrains();
        disableCRUDButtons();// Afficher tous les terrains
    }

    @FXML
    private void handleFootButton() {
        currentType = "Foot";
        afficherTerrainsParType(currentType);
        enableCRUDButtons();
        // Afficher uniquement les terrains de foot
    }

    @FXML
    private void handleBasketButton() {
        currentType = "Basket";
        afficherTerrainsParType(currentType);
        enableCRUDButtons();
        // Afficher uniquement les terrains de basket
    }

    @FXML
    private void handleTennisButton() {
        currentType = "Tennis";
        afficherTerrainsParType(currentType);
        enableCRUDButtons();
        // Afficher uniquement les terrains de tennis
    }

    private void afficherTerrains() {
        ObservableList<Terrain> terrains = FXCollections.observableArrayList(terrainDAO.getAllTerrains());
        terrainTable.setItems(terrains); // Mettre à jour la TableView avec tous les terrains
    }

    private void afficherTerrainsParType(String type) {
        ObservableList<Terrain> terrains = FXCollections.observableArrayList(terrainDAO.getTerrainsByType(type));
        terrainTable.setItems(terrains); // Mettre à jour la TableView avec les terrains filtrés par type
    }

    @FXML
    private void handleAddButton(ActionEvent event) {
        if (currentType.isEmpty()) {
            showAlert(AlertType.WARNING, "Veuillez sélectionner un type de terrain avant d'ajouter.");
            return;  // Ne pas ouvrir le formulaire si aucun type n'est sélectionné
        }
        openForm(new Terrain(0, "", currentType, 0.0, ""));
        // Créer un terrain avec le type pré-rempli
    }




    @FXML
    private void handleUpdateButton(ActionEvent event) {
        Terrain selectedTerrain = terrainTable.getSelectionModel().getSelectedItem();
        if (selectedTerrain != null) {
            openForm(selectedTerrain); // Passer le terrain sélectionné pour modification
        } else {
            showAlert(AlertType.WARNING, "Aucun terrain sélectionné.");
        }
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {
        Terrain selectedTerrain = terrainTable.getSelectionModel().getSelectedItem();
        if (selectedTerrain != null) {
            boolean deleted = terrainDAO.deleteTerrain(selectedTerrain.getId());
            if (deleted) {
                showAlert(AlertType.INFORMATION, "Terrain supprimé avec succès.");
                afficherTerrains(); // Mettre à jour la table après suppression
            } else {
                showAlert(AlertType.ERROR, "Erreur lors de la suppression du terrain.");
            }
        } else {
            showAlert(AlertType.WARNING, "Aucun terrain sélectionné.");
        }
    }

    private void openForm(Terrain terrain) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/TerrainForm.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(terrain == null ? "Ajouter un terrain" : "Modifier le terrain");

            TerrainFormController controller = loader.getController();
            controller.setTerrain(terrain, currentType);
            // Correct


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erreur lors de l'ouverture du formulaire.");
        }
    }

    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void disableCRUDButtons() {
        addButton.setDisable(true);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void enableCRUDButtons() {
        addButton.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
    }
}
