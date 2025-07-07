package fr.eni.encheres.bll.impl;

import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bo.*;
import fr.eni.encheres.exception.BusinessException;
import fr.eni.encheres.dal.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class ArticleVenduServiceImpl implements ArticleVenduService {

    private ArticleVenduDAO articleVenduDAO;

    private UtilisateurDAO utilisateurDAO;

    private EnchereDAO enchereDAO;

    private CategorieDAO categorieDAO;

    private RetraitDAO retraitDAO;


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

        if (lstArticlesVendus != null) {
            lstArticlesVendus.forEach(articleVendu -> {
                setUtilisateurCategorieRetrait1article(articleVendu);
            });
        }

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

          if (articleVendu != null) {
           setUtilisateurCategorieRetrait1article(articleVendu);

            //List<Enchere> lstEncheres = enchereDAO.readByArticle(noArticle);
            //if (lstEncheres != null) {
            //   articleVendu.setLstEncheres(lstEncheres);
            //}

        }

        return articleVendu;
    }

    /** Private method to make the association between an article and their other settings
     * (Utilisateur, Enchere, Categorie, Retrait)
     *
     * @param articleVendu
     */

   private void setUtilisateurCategorieRetrait1article(ArticleVendu articleVendu) {
        //Utilisateur utilisateur = utilisateurDAO.readUtilisateurById(articleVendu.getUtilisateur().getNoUtilisateur());
        //articleVendu.setUtilisateur(utilisateur);
       // Enchere enchere = enchereDAO.readByNoEnchere(articleVendu
        //Categorie categorie = categorieDAO.readCategorie(articleVendu.getCategorie().getNoCategorie());
        //articleVendu.setCategorie(categorie);
        //Retrait retrait = retraitDAO.readByArticle(articleVendu.getNoArticle());
        //articleVendu.setLieuRetrait(retrait);

    }

    /** Method used to know what is the state of the auction (begun, ended, not already began)
     *
     * @param articleVendu
     * @return a String with the actual state of the auction.
     */

    @Override
    public String etatEnchere(ArticleVendu articleVendu){
        LocalDate dateDebut = articleVendu.getDateDebutEncheres();
        LocalDate dateFin = articleVendu.getDateFinEncheres();
        LocalDate today = LocalDate.now();
        if (dateDebut != null && dateFin != null){
            if (today.isBefore(dateDebut)) {
                return "l'enchère n'a pas commencée";
            }
            if (today.isAfter(dateFin)) {
                return "l'enchère est finie";
            }
            else return "l'enchère est en cours";
        }
        return "erreur de la mise en enchère";
    }


    @Override
    public void createArticleVendu(ArticleVendu articleVendu) {

        BusinessException be = new BusinessException();

        articleVenduDAO.createArticle(articleVendu);
    }

    @Override
    public void updateArticleVendu(ArticleVendu articleVendu) {
        articleVenduDAO.updateArticle(articleVendu);
    }

    @Override
    public void deleteArticleVendu(ArticleVendu articleVendu) {
        long noArticle = articleVendu.getNoArticle();
        articleVenduDAO.removeArticle(noArticle);
    }

    @Override
    public String getNameArticleVendu(long noArticle) {
        return articleVenduDAO.findNomArticle(noArticle);
    }


}
