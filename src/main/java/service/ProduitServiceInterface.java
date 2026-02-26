package service;

import java.util.List;

import dto.ProduitDTO;


public interface ProduitServiceInterface {
    List<ProduitDTO> retreive();
    void create(ProduitDTO dto);
    void update(ProduitDTO dto, int id);
    boolean delete(int id);
    ProduitDTO getProduitDTO(int id);
}