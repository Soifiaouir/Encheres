package fr.eni.encheres.bll;

import fr.eni.encheres.bo.ArticleVendu;

import java.util.List;

public interface ArticleVenduService {

    List<ArticleVendu> getLstArticleVendus();

    ArticleVendu getArticleVenduByNoArticle(long noArticle);

    String etatEnchere(ArticleVendu articleVendu);

    void createArticleVendu(ArticleVendu articleVendu);

    void updateArticleVendu(ArticleVendu articleVendu);

    void deleteArticleVendu(ArticleVendu articleVendu);

    String getNameArticleVendu(long noArticle);

}
