package fr.eni.encheres.ihm;

import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.List;

@SessionAttributes({"userSession"})
@Controller
public class VenteController {

    ArticleVenduService articleVenduService;

    UtilisateurService utilisateurService;

    CategorieService categorieService;

    public VenteController(ArticleVenduService articleVenduService, UtilisateurService utilisateurService, CategorieService categorieService) {
        this.articleVenduService = articleVenduService;
        this.utilisateurService = utilisateurService;
        this.categorieService = categorieService;
    }


    @GetMapping("/list_articles")
    public String pagesListesArticles(@ModelAttribute("userSession") Utilisateur utilisateur, Model model) {
        List<ArticleVendu> list = articleVenduService.getLstArticleVendusbyUtilisateur(utilisateur);
        model.addAttribute("articlesLst", list);

        int articleListSize = list.size();
        model.addAttribute("nmbArticles", articleListSize);

        return "view_articles_list";

    }

    @GetMapping("/creer_article")
    public String PageVendreUnArticle( @ModelAttribute("userSession") Utilisateur utilisateurEnSession, Model model) {
        ArticleVendu article = new ArticleVendu();
        model.addAttribute("articleVendu", article);

        List<Categorie> categorieList = categorieService.findAll();
        model.addAttribute("categorieList", categorieList);
       // Long noUtilisateur = utilisateurEnSession.getNoUtilisateur();
       // model.addAttribute("noUtilisateur", noUtilisateur);

        return "create_article";
    }

    @PostMapping ("/creer_article")
    public String PageVendreUnArticlePost(@ModelAttribute("articleVendu") ArticleVendu articleVendu,
                                          BindingResult bindingResult,
                                          @ModelAttribute("utilisateurEnSession") Utilisateur utilisateurEnSession) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "redirect:/creer_article";
        }

        articleVendu.setUtilisateur(utilisateurEnSession);
        articleVendu.setPrixVente(articleVendu.getPrixInitial());
        articleVendu.setCategorie(articleVendu.getCategorie());

        articleVenduService.createArticleVendu(articleVendu);

        return "redirect:/list_articles";

    }

    @GetMapping("/fiche_article")
    public String PageVenteNonCommencee(@RequestParam(name="noArticle") long noArticle,
                                        Model model) {
        ArticleVendu articleVendu = articleVenduService.getArticleVenduByNoArticle(noArticle);
        model.addAttribute("articleVendu", articleVendu);
        List<Categorie> categorieList = categorieService.findAll();
        model.addAttribute("categorieList", categorieList);

        System.out.println(articleVendu);

        return "modifie_article";

    }

    @PostMapping ("/fiche_article")
    public String PageVenteNonCommenceePost(@ModelAttribute("articleVendu") ArticleVendu articleVendu,
                                          BindingResult bindingResult,
                                            @ModelAttribute("utilisateurEnSession") Utilisateur utilisateurEnSession) {
        if (bindingResult.hasErrors()) {
            return "modifie_article";
        }

        articleVendu.setNoArticle(articleVendu.getNoArticle());
        articleVendu.setUtilisateur(utilisateurEnSession);
        articleVendu.setPrixVente(articleVendu.getPrixInitial());
        articleVendu.setCategorie(articleVendu.getCategorie());

        System.out.println("Nouvel articleVendu = " + articleVendu);

        articleVenduService.updateArticleVendu(articleVendu);

        return "redirect:/list_articles";

        }



    }








