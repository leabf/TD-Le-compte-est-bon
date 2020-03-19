package application;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;
import modele.Modele;
import vue.Main;

public class Controller {
	@FXML
	TextField pseudo;
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
	private void initialize() {
		jeu.setDisable(true);
		chronometre.setDisable(true);
		nombreATrouver.setText(Integer.toString(modele.getNombreCible()));
		pseudo.setEditable(true);
		setClock();
	}
	@FXML
	public void boutonJouer(ActionEvent e) {
		if(pseudo.getText().length() >= 3 && pseudo.getText().length() <= 8) {
			String pseudoSaisi = pseudo.getText(); 
			modele.preparer(pseudoSaisi);
			jeu.setDisable(false);
			chronometre.setDisable(false);
			pseudo.setEditable(false);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setContentText("Votre pseudo doit faire entre 3 et 8 caractères.");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void afficherScore(ActionEvent e) {
		List<String> scores = modele.getGereScore().affiche();
		String scoresAAfficher = "";
		for(String score : scores) {
			scoresAAfficher = scoresAAfficher + "/n";
		}
		Alert affichageScores = new Alert(AlertType.INFORMATION);
		affichageScores.setTitle("Meilleurs Scores");
		affichageScores.setHeaderText("");
		affichageScores.setContentText(scoresAAfficher);
		affichageScores.showAndWait();
		//afficher les scores dans une fenêtre (la liste des scores)
		try {
			modele.getGereScore().export();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//enregistrer les scores dans un fichier html 
		//ouvrir le fichier html dans un navigateur
	}
	
	@FXML
	public void actionBoutons(ActionEvent e) {
		String action = ((Button)e.getSource()).getText();
		switch (action) {
		case "Annuler":
			modele.annuler();
			update();
			break;
		case "Valider":
			modele.valider();
			calcul.setText(modele.getListEtapes().get(modele.getListEtapes().size() - 1).getCalcul());
			update();
			break;
		case "Supprimer":
			modele.supprimer();
			update();
			break;
		case "Proposer":
			modele.proposer();
			update();
			break;

		default:
			break;
		}
	}
	
	public void update() {
		switch (modele.getModeJeu()) {
		case INIATIALISER:
			chrono.setText("03:00");
			break;

		default:
			break;
		}
	}
	
	private void setClock() {
		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			LocalTime currentTime = LocalTime.now();
			heure.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
		}),
			new KeyFrame(Duration.seconds(1))
		);
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();
	}
}
