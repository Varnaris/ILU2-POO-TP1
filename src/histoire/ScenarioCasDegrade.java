package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Etal etal = new Etal();
		etal.libererEtal();
		System.out.println("Fin du test");
		
		Village village = new Village("le village des irréductibles", 10, 5);
		Gaulois bonemine = new Gaulois("Bonemine", 7);
		Gaulois obelix = new Gaulois("Obélix", 25);
		
		System.out.println(village.installerVendeur(bonemine, "fleurs", 20));

		System.out.println(village.rechercherVendeursProduit("fleurs"));
		Etal etalFleur = village.rechercherEtal(bonemine);
		System.out.println(etalFleur.acheterProduit(-10, obelix));
	}

}
