package fr.eni.encheres.dal;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Utilisateur;

import java.util.List;

public interface ArticleVenduDAO {

    ArticleVendu getArticleVendu(long noArticle);

    void createArticle (ArticleVendu articleVendu);

    void updateArticle(ArticleVendu articleVendu);

    void removeArticle (long noArticle);

    List<ArticleVendu> getAllArticleVendu();

    String findNomArticle (long noArticle);

    int findPrixEnchere (long noArticle);

    List<ArticleVendu> getListArticlesVenduByUtilisateur(Utilisateur utilisateur);

    List<ArticleVendu> getListArticlesVenduByUtilisateurAndEtatvente(Utilisateur utilisateur, Integer etatvente);


    List<ArticleVendu> getListArticlesVenduByCategorie(long noCategorie);
}
