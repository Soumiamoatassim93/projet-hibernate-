package controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import bo.Commande;
import dto.ClientDTO;
import dto.CommandeDTO;
import dto.Ligne_CommandeDTO;
import service.CommandeService;

public class CommandeController {

	public CommandeDTO createCommande() {
		
		return CommandeService.createCommandDTO();
		
	}
public CommandeDTO getCommande() {
		
		return new CommandeService().getCommandeDTO();
		
	}
	public void  ajouterLigne(Ligne_CommandeDTO ligne) {
		 new CommandeService().addLigne(ligne);
		
	}
	public void associerClient(ClientDTO dto) {
		new CommandeService().addClient(dto);
		
	}
	public void saveCommande(CommandeDTO commande) {
		new CommandeService().save(commande); 
		
	}
	public void initialiserCommande() {
		new CommandeService().initialiserCommande();
		
	}

	public List<Commande> getAllCommandes() {
		return new CommandeService().getAllCommandes();
	}

}
