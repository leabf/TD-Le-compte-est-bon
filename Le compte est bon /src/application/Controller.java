package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
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
	Label chrono;
	
	@FXML
	SplitPane jeu;
	@FXML
	SplitPane chronometre;
	
	@FXML
	HBox plaquesBox;
	
	Modele modele = new Modele();
	Main main = new Main();
	private int timeMin;
	private int timeSec;
	private Integer idPlaque1 = null;
	private Integer idPlaque2 = null;
	private String operation = null;
	private Timeline timeline;
	private List<String> calculHistory = new ArrayList<String>();
	
	@FXML
	private void initialize() {
		jeu.setDisable(true);
		chronometre.setDisable(true);
		nombreATrouver.setText(Integer.toString(modele.getNombreCible()));
		pseudo.setEditable(true);
		setClock();
		setTimer();
	}
	
	@FXML
	public void boutonJouer(ActionEvent e) {
		if(pseudo.getText().length() >= 3 && pseudo.getText().length() <= 8) {
			String pseudoSaisi = pseudo.getText(); 
			modele.preparer(pseudoSaisi);
			jeu.setDisable(false);
			chronometre.setDisable(false);
			pseudo.setEditable(false);
			createPlaques();
			startTimer();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setContentText("Votre pseudo doit faire entre 3 et 8 caractères.");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void afficherScore(ActionEvent e) {
		calculeEnregistreEtAfficheScores();
	}
	
	private void calculeEnregistreEtAfficheScores() {
		//afficher les scores dans une fenêtre (la liste des scores)
		List<String> scores = modele.getGereScore().affiche();
		String scoresAAfficher = "";
		for(String score : scores) {
			scoresAAfficher += score + "\n";
		}
		Alert affichageScores = new Alert(AlertType.INFORMATION);
		affichageScores.setTitle("Meilleurs Scores");
		affichageScores.setHeaderText("");
		affichageScores.setContentText(scoresAAfficher);
		affichageScores.showAndWait();
		//enregistrer les scores dans un fichier html 
		try {
			modele.getGereScore().export();
			//ouvrir le fichier html dans un navigateur
			File htmlFile = new File("score.html");
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private void voulezVousRejouer() {
		Alert rejouer = new Alert(AlertType.CONFIRMATION);
		rejouer.setTitle("Confirmation");
		rejouer.setHeaderText(null);
		rejouer.setContentText("Voulez-vous refaire une partie ?");
		
		Optional<ButtonType> answer = rejouer.showAndWait();
		if(answer.get() == ButtonType.OK) {
			modele = null;
			modele = new Modele();
			pseudo.setText("");
			initialize();
			clearAttributes();
			calculHistory.clear();
			historiqueCalculs.setText("");
			calcul.setText("");
		} else {
			System.exit(0);
		}

	}
	
	@FXML
	public void actionBoutons(ActionEvent e) {
		String action = ((Button)e.getSource()).getText();
		switch (action) {
		case "Annuler":
			modele.annuler();
			calcul.setText(" ");
			clearAttributes();
			break;
		case "Valider":
			String sCalcul = modele.getListEtapes().get(modele.getListEtapes().size() - 1).getCalcul();
			calculHistory.add(sCalcul);
			historiqueCalculs.setText("");
			for(String calculToDisplay : calculHistory) {
				historiqueCalculs.appendText(calculToDisplay + "\n");
			}
			modele.valider();
			clearAttributes();
			createPlaques();
			break;
		case "Supprimer":
			modele.supprimer();
			calcul.setText("");
			calculHistory.remove(calculHistory.size() - 1);
			historiqueCalculs.setText("");
			for(String calculToDisplay : calculHistory) {
				historiqueCalculs.appendText(calculToDisplay + "\n");
			}			createPlaques();
			clearAttributes();
			break;
		case "Proposer":
			modele.getListEtapes().get(modele.getListEtapes().size()-1).setId1(idPlaque1);
			timeline.pause();
			int dureeRestante = timeSec + timeMin * 60;
			modele.setDuree(dureeRestante);
			modele.proposer();
			calculeEnregistreEtAfficheScores();
			voulezVousRejouer();
			break;

		default:
			break;
		}
	}
	
	private void clearAttributes() {
		idPlaque1 = null;
		idPlaque2 = null;
		operation = null;
	}
	
	@FXML
	public void actionOperations(ActionEvent e) {
		operation = ((Button)e.getSource()).getText();
		if(operation != null && idPlaque1 != null && idPlaque2 != null) {
			modele.boutonsSelectiones(idPlaque1, idPlaque2, operation);
			calcul.setText(modele.getListEtapes().get(modele.getListEtapes().size() - 1).getCalcul());
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
	
	private void setTimer() {
		timeSec = 0;
		timeMin = 3;
		chrono.setText(String.format("%02d:%02d", timeMin, timeSec));
	}
	
	private void startTimer() {
		timeline = new Timeline();
		KeyFrame keyframe = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(timeSec <= 0) {
					timeMin--;
					timeSec = 59;
				} else {
					timeSec--;
				}
				if(timeMin == 0) {
					chrono.setTextFill(Color.RED);
				}
				if(timeMin == 0 && timeSec == 0) {
					timeline.pause();
					modele.proposer();
					int dureeRestante = timeSec + timeMin * 60;
					modele.setDuree(dureeRestante);
				}
				chrono.setText(String.format("%02d:%02d", timeMin, timeSec));
			}
		});
		chrono.setTextFill(Color.BLACK);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(keyframe);
		timeline.play();
	}
	
	private void createPlaques() {
		List<Integer> plaques = modele.getListEtapes().get(modele.getListEtapes().size() - 1).getTabPlaques();
		if(!plaquesBox.getChildren().isEmpty()) {
			plaquesBox.getChildren().clear();
		}
		for(int i = 0 ; i < plaques.size() ; i++) {
			Button plaqueButton = new Button();
			plaqueButton.setText(Integer.toString(plaques.get(i)));
			plaqueButton.setScaleY(1);
			plaqueButton.setScaleX(1);
			final int positionPlaque = i;
			plaqueButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					actionPlaque(positionPlaque);					
				}
			});
			plaquesBox.getChildren().add(plaqueButton);
		}
	}
	
	private void actionPlaque(int positionPlaque) {
		if (idPlaque1 == null) {
			idPlaque1 = positionPlaque;
		} else if (idPlaque1 != null && idPlaque2 == null) {
			idPlaque2 = positionPlaque;
		}
		if(idPlaque1 == idPlaque2) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setContentText("Vous ne pouvez pas sélectionner deux fois la même plaque.");
			alert.showAndWait();
			clearAttributes();
			calcul.setText("");
		}
		if(idPlaque1 != null && idPlaque2 != null && operation != null) {
			modele.boutonsSelectiones(idPlaque1, idPlaque2, operation);
			calcul.setText(modele.getListEtapes().get(modele.getListEtapes().size() - 1).getCalcul());
		}
	}
	
}
