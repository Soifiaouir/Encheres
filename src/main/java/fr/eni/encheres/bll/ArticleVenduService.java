package fr.eni.encheres.bll;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Utilisateur;

import java.util.List;

public interface ArticleVenduService {

    List<ArticleVendu> getLstArticleVendus();

    ArticleVendu getArticleVenduByNoArticle(long noArticle);

    List<ArticleVendu> getLstArticleVendusbyUtilisateur(Utilisateur utilisateur);

    String etatEnchere(ArticleVendu articleVendu);

    void createArticleVendu(ArticleVendu articleVendu);

    void updateArticleVendu(ArticleVendu articleVendu);

    void deleteArticleVendu(ArticleVendu articleVendu);

    String getNameArticleVendu(long noArticle);

}
