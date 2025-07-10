package fr.eni.encheres.bll.impl;

import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bo.*;
import fr.eni.encheres.exception.BusinessException;
import fr.eni.encheres.dal.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class ArticleVenduServiceImpl implements ArticleVenduService {


    private ArticleVenduDAO articleVenduDAO;

    private UtilisateurDAO utilisateurDAO;

    private EnchereDAO enchereDAO;

    private CategorieDAO categorieDAO;

    private RetraitDAO retraitDAO;

    @Autowired
    public ArticleVenduServiceImpl(ArticleVenduDAO articleVenduDAO) {
        this.articleVenduDAO = articleVenduDAO;
    }

    /** Method used to get the list of all the articles
     *
     * @return
     */

    @Override
    public List<ArticleVendu> getLstArticleVendus() {
        List<ArticleVendu> lstArticlesVendus = articleVenduDAO.getAllArticleVendu();
        return lstArticlesVendus;
    }

    /** Method used to get the list of articles selled by someone et by the state of the auction
     *
     * @param utilisateur
     * @return list
     */

    @Override
    public List<ArticleVendu> getLstArticleVendusbyUtilisateurAndEtatvente(Utilisateur utilisateur, Integer etatvente) {
        List<ArticleVendu> lstArticlesVendus = articleVenduDAO.getListArticlesVenduByUtilisateurAndEtatvente(utilisateur, etatvente);
        return lstArticlesVendus;
    }

    /** Method used to get the list of articles selled by someone
     *
     * @param utilisateur
     * @return list
     */


    @Override
    public List<ArticleVendu> getLstArticleVendusbyUtilisateur(Utilisateur utilisateur) {
        List<ArticleVendu> lstArticlesVendus = articleVenduDAO.getListArticlesVenduByUtilisateur(utilisateur);
        return lstArticlesVendus;
    }

    /** Method used to get one article by his id (noArticle)
     *
     * @param noArticle
     * @return the article
     */

    @Override
    public ArticleVendu getArticleVenduByNoArticle(long noArticle) {
        ArticleVendu articleVendu = articleVenduDAO.getArticleVendu(noArticle);

        return articleVendu;
    }

    /**
     * method to get the list of article by categorie id
     * @param noCategorie
     * @return
     */
    @Override
    public List<ArticleVendu> getLstArticleVendusByCategorie(long noCategorie) {
        return articleVenduDAO.getListArticlesVenduByCategorie(noCategorie);
    }


    /** Method used to know what is the state of the auction (begun, ended, not already began)
     *
     * @param articleVendu
     * @return a number that returns the state of the auction (0 : problems, 1 : not alreadybegan, 2, begun, 3, ended.)
     */

    @Override
    public Integer etatEnchere(ArticleVendu articleVendu){
        LocalDate dateDebut = articleVendu.getDateDebutEncheres();
        LocalDate dateFin = articleVendu.getDateFinEncheres();
        LocalDate today = LocalDate.now();
        if (dateDebut != null && dateFin != null){
            if (today.isBefore(dateDebut)) {
                return 1;
            }
            if (today.isAfter(dateFin)) {
                return 3;
            }
            else return 2;
        }
        return 0;
    }

    /** Method used to create an article
     *
     * @param articleVendu
     */

    @Override
    public void createArticleVendu(ArticleVendu articleVendu) {

        articleVenduDAO.createArticle(articleVendu);
    }

    @Override
    public void updateArticleVendu(ArticleVendu articleVendu) {
        articleVenduDAO.updateArticle(articleVendu);
    }

    /** Method used to delete an Article
     *
     * @param articleVendu
     */

    @Override
    public void deleteArticleVendu(ArticleVendu articleVendu) {
        long noArticle = articleVendu.getNoArticle();
        articleVenduDAO.removeArticle(noArticle);
    }

    /** Method used to get the name of an article
     *
     * @param noArticle
     * @return a String with the name of the Article
     */

    @Override
    public String getNameArticleVendu(long noArticle) {
        return articleVenduDAO.findNomArticle(noArticle);
    }

    /**
     * Method used to get the date before an auction
     *
     * @param dateDebutEnchere
     * @return
     */
    @Override
    public Long getCalendrierEnchere(LocalDate dateDebutEnchere, LocalDate dateFinEnchere) {
        LocalDate today = LocalDate.now();
        if (dateDebutEnchere.isAfter(today)){
            return ChronoUnit.DAYS.between(today, dateDebutEnchere);
        }
        else {
            return ChronoUnit.DAYS.between(today, dateFinEnchere);
        }
    }
}
