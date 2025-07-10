package fr.eni.encheres.bll.impl;

import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.ArticleVenduDAO;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.exception.BusinessCode;
import fr.eni.encheres.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService {


    private EnchereDAO enchereDAO;
    private ArticleVenduDAO articleVenduDAO;



    @Autowired
    public EnchereServiceImpl(EnchereDAO enchereDAO, ArticleVenduDAO articleVenduDAO) {
        this.enchereDAO = enchereDAO;
        this.articleVenduDAO = articleVenduDAO;


    }


    @Override
    public Enchere findById(long noEnchere) {
       Enchere e =  enchereDAO.readByNoEnchere(noEnchere);
       return e;
    }

    @Override
    public List<Enchere> findByArticle(long noArticle) {
        return enchereDAO.readByArticle(noArticle);
    }

    @Override
    public List<Enchere> findByUser(long noUtilisateur) {
        return enchereDAO.readByUser(noUtilisateur);
    }

    @Override
    public List<Enchere> findListEnchere() {
        List<Enchere> listEnchere=enchereDAO.readListEnchere();


        return listEnchere;
    }
    @Override
    public void createEnchere(Enchere enchere, Long noArticle) {
        BusinessException be = new BusinessException();

        boolean isValid = true;
//        isValid &= validateDateEnchere(enchere.getDateEnchere(), be);
        isValid &= validatePrixEnchere(enchere,be);
//        isValid &= validateUtilisateur(enchere.getEncherisseur().getNoUtilisateur(), be);
//
        if (isValid) {
            try {
                enchereDAO.createEnchere(enchere, noArticle);
            } catch (DataAccessException e) {// Exception de la couche DAL
                // Rollback automatique
                be.add(BusinessCode.BLL_ENCHERE_CREATION_ECHOUEE);
                throw be;
            }

        } else {
            throw be;
        }

    }

    /**
     * Methode that has to check the date of the bid is valid and respect the date of the article sell
     * @param dateEnchere
     * @param be
     * @return
     */
    private boolean validateDateEnchere(LocalDate dateEnchere, BusinessException be) {
        Enchere e = new Enchere();

        if (dateEnchere.isAfter(e.getArticleConcerne().getDateDebutEncheres()) && dateEnchere.isBefore(e.getArticleConcerne().getDateFinEncheres()) ) {
            return true;
        }
        else{
            be.add(BusinessCode.ERREUR_DATE_ENCHERE);
            return false;
        }
    }

    /**
     *Methode mate to validate the price of the bid by comparing it to the price of the last dib made and the initial price
     * @param enchere
     * @param be
     * @return
     */
    private boolean validatePrixEnchere(Enchere enchere, BusinessException be) {
        //verifie la presence de l'article dans la bdd
        if (enchere == null || enchere.getArticleConcerne() == null) {
            be.add(BusinessCode.ERREUR_ARTICLE_INEXISTANT);
            return false;
        }

        ArticleVendu article = enchere.getArticleConcerne();
        Enchere derniere = article.getDerniereEnchere();
        int montantEnchere = enchere.getMontantEnchere();

        if (montantEnchere > article.getPrixInitial() &&
                (derniere == null || montantEnchere > derniere.getMontantEnchere())) {
            return true;
        } else {
            be.add(BusinessCode.ERREUR_MONTANT_ENCHERE);
            return false;
        }
    }
        private boolean validateUtilisateur(Long noUtilisateur,BusinessException be) {
        if  (noUtilisateur == null) {
            return true;
        }else
            be.add(BusinessCode.ERROR_UTILISATEUR_INEXISTANT);
        return false;

    }


    @Override
    public void updateEnchere(Enchere enchere) {
            enchereDAO.updateEnchere(enchere);
    }

    @Override
    public void deleteEnchere(long noEnchere) {
            enchereDAO.deleteEnchere(noEnchere);
    }


}
