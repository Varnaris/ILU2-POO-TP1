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

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int idxEtalLibre = marche.trouverEtalLibre();
		
		if (idxEtalLibre == -1) {
			chaine.append("Malheureusement tous les étals sont déjà occupés !");
			return chaine.toString();
		}
		marche.utiliserEtal(idxEtalLibre, vendeur, produit, nbProduit);
		chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (idxEtalLibre+1) + ".\n");
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] etals = marche.trouverEtals(produit);
		if (etals.length == 0) {
			return "Malheureusement, aucun vendeur ne propose de " + produit + "sur le marché.";
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
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village «" + nom + "» possède plusieurs étals :\n");
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}
}