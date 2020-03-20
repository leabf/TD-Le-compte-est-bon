package modele;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Modele {
	private static final int DUREE_MAX = 180;

	private int nombreCible;
	private List<Etape> listEtapes;
	private int dureeRestante;
	private GereScore gereScore; 
	private String pseudo; 
	
	public List<Etape> getListEtapes() {
		return listEtapes;
	}
	public void setDuree(int duree) {
		this.dureeRestante = duree;
	}
	public GereScore getGereScore() {
		return gereScore;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public int getNombreCible() {
		return this.nombreCible;
	}
	
	public Modele() {
		pseudo = "";
		listEtapes  = new LinkedList<Etape>();
		listEtapes.add(new Etape());
		gereScore = new GereScore();
		Random r = new Random();
		nombreCible = 100 + r.nextInt(999-100);
	}
	
	public void preparer(String pseudo) {
		this.pseudo = pseudo;
		dureeRestante = 0;
	}
	
	public void boutonsSelectiones(int plaque1, int plaque2, String operation) {
		listEtapes.add(new Etape(listEtapes.get(listEtapes.size() - 1).getTabPlaques(), plaque1, plaque2, operation));
	}
	
	public void annuler() {
		final int LAST_ELEMENT = listEtapes.size() - 1;
		
		if((listEtapes.get(LAST_ELEMENT).getId1() != null) && (listEtapes.get(LAST_ELEMENT).getId2() != null) &&
				(listEtapes.get(LAST_ELEMENT).getIdOperation() != null) && !(listEtapes.get(LAST_ELEMENT).isCalculOK())) {
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
		int scoreObtenu = nombreCible - listEtapes.get(listEtapes.size() - 1).getTabPlaques().get(listEtapes.get(listEtapes.size() - 1).getId1());
		scoreObtenu = scoreObtenu > 0 ? scoreObtenu : -scoreObtenu;
		gereScore.ajouteScore(pseudo, scoreObtenu, DUREE_MAX - dureeRestante);
		gereScore.enregistre();
	}
}
