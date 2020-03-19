package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	Button annuler;
	@FXML
	Button valider;
	@FXML 
	Text calcul;
	
	@FXML
	TextArea historiqueCalculs;
	@FXML
	Button supprimer;
	@FXML
	Text nombreATrouver;
	@FXML
	Button proposer;
	
	@FXML
	Text chrono;
	
	Modele modele;
	Main main;
	
	
}
