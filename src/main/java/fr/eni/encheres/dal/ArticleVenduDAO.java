package fr.eni.encheres.dal;

import fr.eni.encheres.bo.ArticleVendu;

import java.util.List;

public interface ArticleVenduDAO {

    ArticleVendu getArticleVendu(long id);

    void createArticle (ArticleVendu articleVendu);

    void updateArticle(ArticleVendu articleVendu);

    void removeArticle (long id);

    List<ArticleVendu> getAllArticleVendu();

    String findNomArticle (long id);

    int findPrixEnchere (long id);

}
