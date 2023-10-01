package villagegaulois;

import personnages.*;

public class Marche {
	private Etal[] etals;
	
	public Marche(int nbEtals) {
		etals = new Etal[nbEtals];
		for (int i = 0; i < nbEtals; i++) {
			etals[i] = new Etal();
		}
	}
	
	public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
		etals[indiceEtal].occuperEtal(vendeur,produit,nbProduit);
	}
	
	public int trouverEtalLibre() {
		for (int i = 0; i < etals.length; i++) {
			if (!etals[i].isEtalOccupe()) {
				return i;
			}
		}
		return -1;
	}
	
	public Etal[] trouverEtals(String produit) {
		int tailleTab = 0;
		for (Etal etal : etals) {
			if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
				tailleTab++;
			}
		}
		
		Etal[] etalsContenantProduit = new Etal[tailleTab];
		int j = 0;
		for (Etal etal : etals) {
			if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
				etalsContenantProduit[j] = etal;
				j++;
			}
		}
		return etalsContenantProduit;
	}
	
	public Etal trouverVendeur(Gaulois gaulois) {
		for (Etal etal : etals) {
			if (etal.getVendeur() == gaulois) {
				return etal;
			}
		}
		return null;
	}
	private String afficherEtalOccupe(Etal etal) {
		assert etal.isEtalOccupe();
		String[] splitTab = etal.afficherEtal().split(" ");
		return etal.getVendeur().getNom() + " vend " + splitTab[splitTab.length-2] + " " + splitTab[splitTab.length-1];
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		int etalsVide = 0;
		for (Etal etal : etals) {
			if (etal.isEtalOccupe()) {
				chaine.append(afficherEtalOccupe(etal));
			}
			else{
				etalsVide++;
			}
		}
		if(etalsVide > 0) {
			chaine.append("Il reste " + etalsVide + " étals non utilisés dans le marché.\n");
		}
		return chaine.toString();
	}
}
