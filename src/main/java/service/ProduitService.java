package service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import bo.Client;
import bo.Produit;
import dao.ProduitDAO;
import dto.ClientDTO;
import dto.CommandeDTO;
import dto.Ligne_CommandeDTO;
import dto.ProduitDTO;

public class ProduitService implements ProduitServiceInterface{

	@Override
	public List<ProduitDTO> retreive() {
		
		 List<ProduitDTO> produitdtos=new ProduitDAO().retreive().stream().map(p->fromProduit(p)).collect(Collectors.toList());
		 return produitdtos;
	}
	public ProduitDTO fromProduit(Produit produit) {
		ProduitDTO produitdto=new ProduitDTO();
		produitdto.setId(produit.getId());
		produitdto.setQtstock(produit.getQtstock());
		produitdto.setLibelle(produit.getLibelle());
		produitdto.setPrix(produit.getPrix());
		
		return produitdto;
		
		
	}
	public Produit toProduit(ProduitDTO produitdto) {
		Produit produit=new Produit();
		produit.setId(produitdto.getId());
		produit.setQtstock(produitdto.getQtstock());
		produit.setLibelle(produitdto.getLibelle());
		produit.setPrix(produitdto.getPrix());
		
		return produit;
		
		
	}
	public void decrease_stock() {
		CommandeDTO cmd=new CommandeService().getCommandeDTO();
		for(Ligne_CommandeDTO l:cmd.getLignes()) {
			ProduitDTO p=l.getProduit();
			p.setQtstock(p.getQtstock()-l.getQuantite());
			new ProduitDAO().update(this.toProduit(p));
		}
		
	}
	
	
	@Override
	public void create(ProduitDTO dto) {
	    new ProduitDAO().create(this.toProduit(dto));
	}

	@Override
	public void update(ProduitDTO dto, int id) {
	    ProduitDAO dao = new ProduitDAO();
	    Produit p = dao.findById(id);
	    if (p != null) {
	        Produit updated = this.toProduit(dto);
	        updated.setId(id);
	        dao.update(updated);
	    }
	}

	@Override
	public boolean delete(int id) {
	    ProduitDAO dao = new ProduitDAO();
	    Produit p = dao.findById(id);
	    if (p != null) return dao.delete(p);
	    return false;
	}

	@Override
	public ProduitDTO getProduitDTO(int id) {
	    ProduitDAO dao = new ProduitDAO();
	    Produit p = dao.findById(id);
	    if (p != null) return this.fromProduit(p);
	    throw new RuntimeException("Produit introuvable!");
	}
}
