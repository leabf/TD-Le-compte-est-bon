package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import modele.Modele;
import vue.Main;

public class Controller {
	@FXML
	Label pseudo;
	@FXML
	Text heure;
	@FXML
	Button jouer;
	@FXML
	Button score;
	
	@FXML 
	Text calcul;
	
	@FXML
	TextArea historiqueCalculs;
	@FXML
	Text nombreATrouver;
	
	@FXML
	Text chrono;
	
	@FXML
	SplitPane jeu;
	@FXML 
	SplitPane chronometre;
	
	Modele modele = new Modele();
	Main main = new Main();
	
	@FXML
	public void boutonJouer(ActionEvent e) {
		if(pseudo.getText().length() >= 3 && pseudo.getText().length() <= 8) {
			String pseudoSaisi = pseudo.getText(); 
			modele.preparer(pseudoSaisi);
			jeu.setVisible(true);
			chronometre.setVisible(true);
			pseudo.setDisable(true);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setContentText("Votre pseudo doit faire entre 3 et 8 caractères.");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void afficherScore(ActionEvent e) {
		//afficher les scores dans une fenêtre (la liste des scores)
		//enregistrer les scores dans un fichier html 
		//ouvrir le fichier html dans un navigateur
	}
	
	@FXML
	public void actionBoutons(ActionEvent e) {
		String action = ((Button)e.getSource()).getText();
		switch (action) {
		case "Annuler":
			modele.annuler();
			break;
		case "Valider":
			modele.valider();
			calcul.setText(modele.getListEtapes().get(modele.getListEtapes().size() - 1).getCalcul());
			break;
		case "Supprimer":
			modele.supprimer();
			break;
		case "Proposer":
			modele.proposer();
			break;

		default:
			break;
		}
	}
	
}
