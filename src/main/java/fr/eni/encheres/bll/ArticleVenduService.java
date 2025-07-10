package fr.eni.encheres.bll;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

import java.time.LocalDate;
import java.util.List;

public interface ArticleVenduService {

    List<ArticleVendu> getLstArticleVendus();

    ArticleVendu getArticleVenduByNoArticle(long noArticle);

    List<ArticleVendu> getLstArticleVendusbyUtilisateurAndEtatvente(Utilisateur utilisateur, Integer etatvente);

    List<ArticleVendu> getLstArticleVendusbyUtilisateur(Utilisateur utilisateur);

    Integer etatEnchere(ArticleVendu articleVendu);

    void createArticleVendu(ArticleVendu articleVendu);

    void updateArticleVendu(ArticleVendu articleVendu);

    void deleteArticleVendu(ArticleVendu articleVendu);

    String getNameArticleVendu(long noArticle);

    Long getCalendrierEnchere(LocalDate dateDebutEnchere, LocalDate dateFinEnchere);
    List<ArticleVendu> getLstArticleVendusByCategorie(long Categorie);



}
