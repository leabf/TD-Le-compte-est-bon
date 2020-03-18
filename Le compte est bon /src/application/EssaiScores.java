package application;

import java.io.IOException;

public class EssaiScores {

	public static void main(String[] args) {
		GereScore gereScore = new GereScore();
		gereScore.ajouteScore("Georges", 0, 67);
		gereScore.ajouteScore("Lucien", 15, 220);
		gereScore.ajouteScore("Marcel", 0, 67);
		gereScore.ajouteScore("Georges", 2, 84);
		gereScore.ajouteScore("Renee", 0, 155);
		
		gereScore.affiche();
		
		gereScore.enregistre();
		try {
			gereScore.export();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
