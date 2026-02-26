package controller;

import java.util.List;

import dto.CommandeDTO;
import dto.Ligne_CommandeDTO;
import dto.ProduitDTO;
import service.CommandeService;
import service.ProduitService;

public class ProduitController {
public List<ProduitDTO> getAllProduit(){
	ProduitService produitservice=new ProduitService();
	return produitservice.retreive();
}

public void ajouterProduit(ProduitDTO dto) {
    new ProduitService().create(dto);
}

public void modifierProduit(ProduitDTO dto, int id) {
    new ProduitService().update(dto, id);
}

public boolean supprimerProduit(int id) {
    return new ProduitService().delete(id);
}


public ProduitDTO getProduitDTO(int id) {
    return new ProduitService().getProduitDTO(id);
}


public void decrease_stock(){
	
	ProduitService produitservice=new ProduitService();
	  produitservice.decrease_stock();
}
}
