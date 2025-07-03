package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Categorie;

import java.util.List;

public interface CategorieService {
    void createCategorie(Categorie categorie);
    Categorie findById(long noCategorie);
    void deleteCategorie(long noCategorie);
    List<Categorie> findAll();
}
