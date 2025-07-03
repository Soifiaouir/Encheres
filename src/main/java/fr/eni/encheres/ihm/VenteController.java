package fr.eni.encheres.ihm;

import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.List;


@Controller
public class VenteController {

    ArticleVenduService articleVenduService;

    public VenteController(ArticleVenduService articleVenduService) {
        this.articleVenduService = articleVenduService;
    }

    @GetMapping("/list_articles")
    public String pagesListesEncheres(Model model) {
        List<ArticleVendu> list = articleVenduService.getLstArticleVendus();
        model.addAttribute("articlesLst", list);

        int articleListSize = list.size();
        model.addAttribute("nmbArticles", articleListSize);

        return "view_articles_list";
    }

    @GetMapping("/fiche_article")
    public String PageEnchereNonCommencee(@RequestParam(name="noArticle") long noArticle, Model model) {
        ArticleVendu article = articleVenduService.getArticleVenduByNoArticle(noArticle);
        model.addAttribute("articleVendu", article);

        String nomArticle = article.getNomArticle();

        model.addAttribute("articleNom", nomArticle);

        return "view_article";

    }

    @GetMapping("/creer_article")
    public String PageVendreUnArticle(Model model) {
        model.addAttribute("articleVendu", new ArticleVendu());

        return "create_article";
    }

    @PostMapping ("/creer_article")
    public String PageVendreUnArticlePost(@Valid @ModelAttribute("articleVendu") ArticleVendu articleVendu,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "create_article";
        }

        articleVenduService.createArticleVendu(articleVendu);
        System.out.println(articleVendu);

        return "view_article";

    }



    }








