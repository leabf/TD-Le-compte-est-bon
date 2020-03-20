package modele;

import java.util.LinkedList;
import java.util.List;

public class Modele {
	private static final int DUREE_MAX = 180;
	private static final int NOMBRE_CIBLE = (int) (100 + (Math.random() * (999-100)));

	private List<Etape> listEtapes;
	private int dureeRestante;
	private GereScore gereScore; 
	private String pseudo; 
	private ModeJeu modeJeu;
	
	public List<Etape> getListEtapes() {
		return listEtapes;
	}
	public void setListEtapes(List<Etape> listEtapes) {
		this.listEtapes = listEtapes;
	}
	public int getDuree() {
		return dureeRestante;
	}
	public void setDuree(int duree) {
		this.dureeRestante = duree;
	}
	public GereScore getGereScore() {
		return gereScore;
	}
	public void setGereScore(GereScore gereScore) {
		this.gereScore = gereScore;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public ModeJeu getModeJeu() {
		return modeJeu;
	}
	public void setModeJeu(ModeJeu modeJeu) {
		this.modeJeu = modeJeu;
	}
	public static int getDureeMax() {
		return DUREE_MAX;
	}
	public static int getNombreCible() {
		return NOMBRE_CIBLE;
	}
	
	public Modele() {
		modeJeu = ModeJeu.INIATIALISER;
		pseudo = "";
		listEtapes  = new LinkedList<Etape>();
		listEtapes.add(new Etape());
		gereScore = new GereScore();
	}
	
	public void attendre() {
		modeJeu = ModeJeu.ATTENDRE;
	}
	
	public void preparer(String pseudo) {
		modeJeu = ModeJeu.PREPARER;
		this.pseudo = pseudo;
		dureeRestante = 0;
	}
	
	public void boutonsSelectiones(int plaque1, int plaque2, String operation) {
		modeJeu = ModeJeu.JOUER;
		listEtapes.add(new Etape(listEtapes.get(listEtapes.size() - 1).getTabPlaques(), plaque1, plaque2, operation));
	}
	
	public void annuler() {
		final int LAST_ELEMENT = listEtapes.size() - 1;
		
		if((listEtapes.get(LAST_ELEMENT).getId1() != null) && (listEtapes.get(LAST_ELEMENT).getId2() != null) &&
				(listEtapes.get(LAST_ELEMENT).getIdOperation() != null) && !(listEtapes.get(LAST_ELEMENT).isCalculOK()) && 
				modeJeu == ModeJeu.JOUER) {
			listEtapes.remove(listEtapes.size() - 1);
		}
	}
	
	public void valider() {
		final int LAST_ELEMENT = listEtapes.size() - 1;

		if((listEtapes.get(LAST_ELEMENT).getId1() != null) && (listEtapes.get(LAST_ELEMENT).getId2() != null) &&
				(listEtapes.get(LAST_ELEMENT).getIdOperation() != null)) {
			listEtapes.get(LAST_ELEMENT).validerCalcul();
		}
	}
	
	public void supprimer() {
		if(listEtapes.size() > 1) {
			listEtapes.remove(listEtapes.size() - 1);
		}
	}
	
	public void proposer() {
		modeJeu = ModeJeu.SCORE;
		int scoreObtenu = NOMBRE_CIBLE - listEtapes.get(listEtapes.size() - 1).getTabPlaques().get(listEtapes.get(listEtapes.size() - 1).getId1());
		scoreObtenu = scoreObtenu > 0 ? scoreObtenu : -scoreObtenu;
		gereScore.ajouteScore(pseudo, scoreObtenu, DUREE_MAX - dureeRestante);
		gereScore.enregistre();
	}
}
