package fr.eni.encheres.bll.impl;

import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieServiceImpl implements CategorieService {
    @Override
    public List<Categorie> findAll() {
        return categorieDAO.getAll();
    }

    private CategorieDAO categorieDAO;

    @Autowired
    public CategorieServiceImpl(CategorieDAO categorieDAO) {
        this.categorieDAO = categorieDAO;
    }

    @Override
    public void createCategorie(Categorie categorie) {
        categorieDAO.createCategorie(categorie);
    }

    @Override
    public Categorie findById(long noCategorie) {
        return categorieDAO.readNoCategorie(noCategorie);
    }

    @Override
    public void deleteCategorie(long noCategorie) {
        categorieDAO.deleteCategorie(noCategorie);
    }
}
