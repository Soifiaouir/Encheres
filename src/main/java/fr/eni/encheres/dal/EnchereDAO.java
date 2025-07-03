package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Enchere;

import java.util.List;

public interface EnchereDAO {
    Enchere readByNoEnchere(long noEnchere);

    void createEnchere(Enchere enchere);
    void deleteEnchere(long noEnchere);
    void updateEnchere(Enchere enchere);
    List<Enchere> readByArticle(Long noArticle);
    List<Enchere> readByUser(Long noUtilisateur);

    List<Enchere> findListEncheres(long noArticleVendu);
}
