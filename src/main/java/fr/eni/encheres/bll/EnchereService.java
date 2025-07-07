package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Enchere;

import java.util.List;


public interface EnchereService {
    Enchere findById(long noEnchere);
    List<Enchere> findByArticle(long noArticle);
    List<Enchere> findByUser(long noUtilisateur);
    List<Enchere> findListEnchere();
    void createEnchere(Enchere enchere);
    void updateEnchere(Enchere enchere);
    void deleteEnchere(long noEnchere);

}
