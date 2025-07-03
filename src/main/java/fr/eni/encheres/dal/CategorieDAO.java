package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Categorie;

import java.util.List;

public interface CategorieDAO {
    Categorie readNoCategorie(long noCategorie);
    List<Categorie> getAll();
    void createCategorie(Categorie categorie);
    void updateCategorie(Categorie categorie);
    void deleteCategorie(long noCategorie);
}
