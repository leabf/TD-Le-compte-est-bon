package application;

import java.util.LinkedList;
import java.util.List;

public class TestTriCollection {

	public static void main(String[] args) {
		List<Score> scores = new LinkedList<Score>();
		scores.add(new Score("Georges", 0, 67));
		scores.add(new Score("Lucien", 15, 220));
		scores.add(new Score("Marcel", 0, 67));
		scores.add(new Score("Georges", 2, 84));
		scores.add(new Score("Renee", 0, 155));

		System.out.println("Avant le tri :");
		for(Score score : scores) {
			score.affiche();
		}
		
		scores.sort(null);
		
		System.out.println("Après le tri :");
		for(Score score : scores) {
			score.affiche();
		}
	}

}
