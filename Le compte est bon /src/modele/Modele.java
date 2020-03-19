package modele;

import java.util.LinkedList;
import java.util.List;

public class Modele {
	private static final int DUREE_MAX = 45;
	private static final int NOMBRE_CIBLE = (int) (100 + (Math.random() * (999-100)));

	private List<Etape> listEtapes = new LinkedList<Etape>();
	private int duree;
	private GereScore gereScore = new GereScore();
	private String pseudo = "";
	private ModeJeu modeJeu;
	
}
