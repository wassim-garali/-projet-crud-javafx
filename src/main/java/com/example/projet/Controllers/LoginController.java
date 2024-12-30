package com.example.projet.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.projet.dao.UtilisateurDAO;
import com.example.projet.Models.Utilisateur;

public class LoginController {

    @FXML
    private Label compte_label;
    @FXML
    private ChoiceBox<String> acc_selector;
    @FXML
    private Label adresse_label;
    @FXML
    private TextField adresse_field;
    @FXML
    private Label mdp_label;
    @FXML
    private PasswordField mdp_field;
    @FXML
    private Button login_button;
    @FXML
    private Label erreur_label;

    @FXML
    private Button showPasswordButton; // Bouton pour afficher/masquer le mot de passe

    private boolean passwordVisible = false;

    // Initialisation
    public void initialize() {
        acc_selector.getItems().addAll("Client", "Administrateur");
        acc_selector.setValue("Client");
    }

    @FXML
    public void handleLogin() {
        String adresse = adresse_field.getText();
        String mdp = mdp_field.getText();

        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        Utilisateur utilisateur = utilisateurDAO.verifierUtilisateur(adresse, mdp);

        if (utilisateur != null) {
            String typeCompte = utilisateur.getTypeCompte();
            try {
                FXMLLoader fxmlLoader;
                Scene scene;
                if ("Client".equals(typeCompte)) {
                    fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/utilisateur.fxml"));
                    scene = new Scene(fxmlLoader.load());
                } else if ("Administrateur".equals(typeCompte)) {
                    fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/adminPage.fxml"));
                    scene = new Scene(fxmlLoader.load());
                } else {
                    throw new IllegalArgumentException("Type de compte non valide");
                }

                Stage currentStage = (Stage) login_button.getScene().getWindow();
                currentStage.setScene(scene);
                currentStage.setTitle("Menu " + typeCompte);
            } catch (Exception e) {
                e.printStackTrace();
                erreur_label.setText("Erreur de chargement de l'interface.");
            }
        } else {
            erreur_label.setText("Adresse ou mot de passe incorrect !");
        }
    }

    // Basculer la visibilité du mot de passe
    @FXML
    public void togglePasswordVisibility(ActionEvent actionEvent) {
        passwordVisible = !passwordVisible;
        showPasswordButton.setText(passwordVisible ? "🙈" : "👁");
        mdp_field.setStyle(passwordVisible ? "-fx-background-color: transparent;" : "-fx-background-color: white;");
    }
}
