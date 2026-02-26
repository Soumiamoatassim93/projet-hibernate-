package bo;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Produit  implements Serializable{
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String libelle;
private float prix;
private int qtstock;



@OneToMany(mappedBy = "produit")
private List<Ligne_Commande> lignes;

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getLibelle() {
	return libelle;
}

public void setLibelle(String libelle) {
	this.libelle = libelle;
}

public float getPrix() {
	return prix;
}

public void setPrix(float prix) {
	this.prix = prix;
}

public int getQtstock() {
	return qtstock;
}

public void setQtstock(int qtstock) {
	this.qtstock = qtstock;
}

public List<Ligne_Commande> getLignes() {
	return lignes;
}

public void setCommandes(List<Ligne_Commande> lignes) {
	this.lignes = lignes;
}

public Produit(int id, String libelle, float prix, int qtstock) {
	super();
	this.id = id;
	this.libelle = libelle;
	this.prix = prix;
	this.qtstock = qtstock;
	
}

public Produit() {
	super();
}

@Override
public String toString() {
	return "Produit [id=" + id + ", libelle=" + libelle + ", prix=" + prix + ", qtstock=" + qtstock + "]";
}



}
