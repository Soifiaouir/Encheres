package fr.eni.encheres.ihm;

import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.RetraitService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.exception.BusinessException;
import fr.eni.encheres.ihm.converter.CategorieConverter;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.awt.desktop.UserSessionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SessionAttributes({"userSession"})
@Controller
public class VenteController {

    ArticleVenduService articleVenduService;

    UtilisateurService utilisateurService;

    CategorieService categorieService;

    RetraitService retraitService;

    public VenteController(ArticleVenduService articleVenduService, UtilisateurService utilisateurService, CategorieService categorieService, RetraitService retraitService) {
        this.articleVenduService = articleVenduService;
        this.utilisateurService = utilisateurService;
        this.categorieService = categorieService;
        this.retraitService = retraitService;
    }

    /**
     * Setting a list of article by users
     *
     * @param utilisateur
     * @param model
     * @return
     */

    @GetMapping("/list_articles")
    public String pagesListesArticles(@ModelAttribute("userSession") Utilisateur utilisateur, Model model) {

        /** We separe different lists to show
         *
         */

        List<ArticleVendu> listAvendre = articleVenduService.getLstArticleVendusbyUtilisateurAndEtatvente(utilisateur, 1);
        List<ArticleVendu> listActuelle = articleVenduService.getLstArticleVendusbyUtilisateurAndEtatvente(utilisateur, 2);
        listActuelle.addAll(listAvendre);

        model.addAttribute("articlesLst", listActuelle);
        List<ArticleVendu> listArchive = articleVenduService.getLstArticleVendusbyUtilisateurAndEtatvente(utilisateur, 3);
        model.addAttribute("archiveLst", listArchive);

        /** Method used to know how many articles the user has got on each lists
         *
         */

        int articleListSize = listActuelle.size();
        model.addAttribute("nmbArticles", articleListSize);

        int archiveListSize = listArchive.size();
        model.addAttribute("nmbArchives", archiveListSize);

        return "view_articles_list";

    }

    /**
     * Setting a form to create a new article to sell
     *
     * @param utilisateurEnSession
     * @param model
     * @return
     */

    @GetMapping("/creer_article")
    public String PageVendreUnArticle(@ModelAttribute("userSession") Utilisateur utilisateurEnSession, Model model) {
        ArticleVendu article = new ArticleVendu();
        model.addAttribute("articleVendu", article);

        List<Categorie> categorieList = categorieService.findAll();
        model.addAttribute("categorieList", categorieList);

        LocalDate today = LocalDate.now();
        model.addAttribute("today", today);

        return "create_article";
    }

    @PostMapping("/creer_article")
    public String PageVendreUnArticlePost(@Valid @ModelAttribute("articleVendu") ArticleVendu articleVendu,
                                          BindingResult bindingResult,
                                          @ModelAttribute("userSession") Utilisateur utilisateurEnSession,
                                          Model model) {
        if (bindingResult.hasErrors()) {

            List<Categorie> categorieList = categorieService.findAll();
            model.addAttribute("categorieList", categorieList);
            return "create_article";
        }
        try {
            articleVendu.setUtilisateur(utilisateurEnSession);

            /** Because the article is not on an auction yeat, the actual price is set to the initial price
             *
             */
            articleVendu.setPrixVente(articleVendu.getPrixInitial());

            articleVendu.setEtatVente(articleVenduService.etatEnchere(articleVendu));

            articleVenduService.createArticleVendu(articleVendu);

            return "redirect:/list_articles";

        } catch (BusinessException e) {
            // Ajout des erreurs sur les champs
            e.getFieldErrors().forEach(bindingResult::rejectValue);
        }
        return "create_article";
    }

    /**
     * Setting a form to modifie an article
     */

    @GetMapping("/maj_article")
    public String PageVenteNonCommencee(@RequestParam(name = "noArticle") long noArticle,
                                        Model model) {

        ArticleVendu articleVendu = articleVenduService.getArticleVenduByNoArticle(noArticle);
        model.addAttribute("articleVendu", articleVendu);

        List<Categorie> categorieList = categorieService.findAll();
        model.addAttribute("categorieList", categorieList);

        Utilisateur utilisateur = articleVendu.getUtilisateur();

        System.out.println(utilisateur);

        System.out.println(articleVendu);

        Long joursAvantEnchere = articleVenduService.getCalendrierEnchere(articleVendu.getDateDebutEncheres(), articleVendu.getDateFinEncheres());
        model.addAttribute("joursAvantEnchere", joursAvantEnchere);

        LocalDate today = LocalDate.now();
        model.addAttribute("today", today);

        return "modifie_article";

    }

    @PostMapping("/maj_article")
    public String PageVenteNonCommenceePost(@Valid @ModelAttribute("articleVendu") ArticleVendu articleVendu,
                                            BindingResult bindingResult,
                                            @ModelAttribute("userSession") Utilisateur utilisateurEnSession,
                                            Model model) {
        if (bindingResult.hasErrors()) {

            List<Categorie> categorieList = categorieService.findAll();
            model.addAttribute("categorieList", categorieList);
            return "modifie_article";
        }
        try {
            articleVendu.setUtilisateur(utilisateurEnSession);

            /** Set to know if an auction ad begun. If it hasn't begun, the actual price is the initial price.
             *
             */

            articleVendu.setEtatVente(articleVenduService.etatEnchere(articleVendu));
            if (articleVendu.getEtatVente() < 2) {
                articleVendu.setPrixVente(articleVendu.getPrixInitial());
            }

            System.out.println("Nouvel articleVendu = " + articleVendu);
            System.out.println(articleVendu.getCalendrierEnchere(articleVendu.getDateDebutEncheres(), articleVendu.getDateFinEncheres()));

            articleVenduService.updateArticleVendu(articleVendu);

            return "redirect:/list_articles";

        } catch (BusinessException e) {
            // Ajout des erreurs sur les champs
            e.getFieldErrors().forEach(bindingResult::rejectValue);
        }
        return "modifie_article";

    }
}








