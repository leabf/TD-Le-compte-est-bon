package modele;

import java.util.LinkedList;
import java.util.List;

public class Etape {

	private List<Integer> tabPlaques = new LinkedList<Integer>();
	private String tabOperations[] = {"+", "-", "x", "/"};
	private int id1;
	private int id2;
	private int idOperation;
	private int resultat;
	private boolean calculOK;

}
