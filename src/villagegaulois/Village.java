package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;
import exceptions.VillageSansChefException;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException{
		if (chef == null) {
			throw new VillageSansChefException("Il n'y pas encore de chef dans le village.");
		}
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String afficherVillageoisApp() {
		StringBuilder chaine = new StringBuilder();
		try {
			chaine.append(afficherVillageois());
		} catch (VillageSansChefException e) {
			chaine.append("Il n'y a pas encore de chef au village.");
		}
		return chaine.toString();
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int idxEtalLibre = marche.trouverEtalLibre();

		if (idxEtalLibre == -1) {
			chaine.append("Malheureusement tous les étals sont déjà occupés !");
			return chaine.toString();
		}
		marche.utiliserEtal(idxEtalLibre, vendeur, produit, nbProduit);
		chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (idxEtalLibre + 1)
				+ ".\n");
		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		Etal[] etals = marche.trouverEtals(produit);
		if (etals.length == 0) {
			return "Il n'y a pas de vendeur qui propose des " + produit + " au le marché.";
		}
		StringBuilder chaine = new StringBuilder();
		chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
		for (Etal etal : etals) {
			Gaulois vendeur = etal.getVendeur();
			chaine.append("- " + vendeur.getNom() + "\n");
		}
		return chaine.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}

	public String partirVendeur(Gaulois vendeur) {
		Etal etal = rechercherEtal(vendeur);
		return etal.libererEtal();
	}

	public String afficherMarche() {
		return marche.afficherMarche();
	}
	
	private static class Marche {
		private Etal[] etals;
		
		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur,produit,nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
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
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (Etal etal : etals) {
				if (etal.getVendeur().equals(gaulois)) {
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
		
		private String afficherMarche() {
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
}