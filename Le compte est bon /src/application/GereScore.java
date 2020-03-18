package application;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class GereScore{
	private List<Score> listeScore=new LinkedList<Score>();


	public GereScore() {
		charge();
	}

	public void ajouteScore(String pseudo, int valeur, int temps) {
		listeScore.add(new Score(pseudo, valeur, temps));
		this.listeScore.sort(null);
		while(this.listeScore.size() > 10) {
			this.listeScore.remove(0);
		}
	}

	public void affiche() {
		for(Score score : listeScore) {
			score.affiche();
		}
	}

	public void enregistre() {
		Path path = Paths.get(System.getProperty("user.dir"), "serial.bin");
		ObjectOutputStream oos = null;
		
		try {
			Files.deleteIfExists(path);
			oos = new ObjectOutputStream(Files.newOutputStream(path));
			for(Score score:this.listeScore) {
				oos.writeObject(score);
			}

			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (oos != null) {
				oos.flush();
				oos.close();
			}
		} catch (final IOException ex) {
			ex.printStackTrace();
		}
	}
	public void export() throws IOException {
		try {
			File HTMLFILE= new File("score.html");
			BufferedWriter fichier = new BufferedWriter(new FileWriter(HTMLFILE));

			fichier.write("<HTML>\r\n" + 
					"			<head>\r\n" + 
					"				<title>S C O R E S</title>\r\n" + 
					"			</head>\r\n" + 
					"			<body>\r\n" + 
					"				<h1>S C O R E S</h1>\n");
			for(Score s : this.listeScore) {
				fichier.write("<p><b style=\"color:#B41414\">"+s.getsPseudo()
				+ "="+s.getiValeur()
				+"</b> en "+s.conversionTemps()
				+"<small><small>(le "+s.getsDate()
				+")</small></small></p>\n");
			}

			fichier.write("</body>\r\n" + 
					"</HTML>");

			fichier.close();
		}catch (Exception e) {
			System.err.println(e);
		} 

	}
	public void charge() {
		Path P1 = Paths.get(System.getProperty("user.dir"), "serial.bin");
		if(Files.exists(P1)) {
			boolean bEnd = false;
			ObjectInputStream ois =null;
			try{
				FileInputStream fichier= new FileInputStream("./serial.bin");
				ois = new ObjectInputStream(fichier);
				while(!bEnd) {
					try {
						Score score = (Score) ois.readObject();
						if(score !=null) {
							this.listeScore.add(score);
						}
					}
					catch(EOFException e) {
						bEnd = true;
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			finally {
				try {
					if (ois != null) {
						ois.close();
					}
				} catch (final IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		
	}

}
