package modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Etape {

	private List<Integer> tabPlaques = new LinkedList<Integer>();
	private String tabOperations[] = {"+", "-", "x", "/"};
	private int id1;
	private int id2;
	private int idOperation;
	private int resultat;
	private boolean calculOK = false;
	
	public List<Integer> getTabPlaques() {
		return tabPlaques;
	}
	public void setTabPlaques(List<Integer> tabPlaques) {
		this.tabPlaques = tabPlaques;
	}
	public String[] getTabOperations() {
		return tabOperations;
	}
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public int getId2() {
		return id2;
	}
	public void setId2(int id2) {
		this.id2 = id2;
	}
	public int getIdOperation() {
		return idOperation;
	}
	public void setIdOperation(int idOperation) {
		this.idOperation = idOperation;
	}
	public void setIdOperationWithString(String operation) {
		for(int i = 0 ; i < this.tabOperations.length ; i++) {
			if(this.tabOperations[i].equals(operation)) {
				this.idOperation = i;
				break;
			}
		}
	}
	public int getResultat() {
		return resultat;
	}
	public void setResultat(int resultat) {
		this.resultat = resultat;
	}
	public boolean isCalculOK() {
		return calculOK;
	}
	public void setCalculOK(boolean calculOK) {
		this.calculOK = calculOK;
	}
	public String getCalcul() {
		return tabPlaques.get(id1) + tabOperations[idOperation] + tabPlaques.get(id2) + "=" + resultat;
	}
	
	public Etape() {
		List<Integer> nombresATirer = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,25,50,75,100));
		Random r = new Random();
		for(int i = 0 ; i < 6 ; i++) {
			//Tirage au sort de l'index 
			int index = r.nextInt(nombresATirer.size());
			//Affectation à la liste de la valeur correspondant à l'index tiré
			tabPlaques.add(nombresATirer.get(index));
			//Le nombre tiré est supprimé pour ne pas être tiré 2 fois
			nombresATirer.remove(index);
		}
	}
	
	public Etape(List<Integer> plaque, int id1, int id2, String operation) {
		this.tabPlaques.addAll(plaque);
		this.id1 = id1;
		this.id2 = id2;
		
		setIdOperationWithString(operation);
	}
	
	public void calculer() {
		switch (idOperation) {
		case 1:
			resultat = tabPlaques.get(id1) + tabPlaques.get(id2);
			calculOK = true;
			break;
			
		case 2:
			resultat = tabPlaques.get(id1) - tabPlaques.get(id2);
			calculOK = this.resultat > 0 ? true : false;
			break;
			
		case 3:
			resultat = tabPlaques.get(id1) * tabPlaques.get(id2);
			calculOK = true;
			break;
			
		case 4:
			resultat = tabPlaques.get(id1) / tabPlaques.get(id2);
			calculOK = tabPlaques.get(id1) % tabPlaques.get(id2) == 0 ? true : false;
			break;

		default:
			break;
		}
		setNextEtape();
	}
	
	public void setNextEtape() {
		//Calcule les nouvelles plaques et réinitialise les autres attributs
		if(calculOK) {
			tabPlaques.remove(id1);
			tabPlaques.set(id2, resultat);
			id1 = (Integer) null;
			id2 = (Integer) null;
			idOperation = (Integer) null;
			resultat = 0;
			calculOK = false;
		}
	}

}
