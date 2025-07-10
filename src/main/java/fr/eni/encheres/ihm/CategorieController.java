package fr.eni.encheres.ihm;


import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bo.ArticleVendu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@RequestMapping("/categorie")
@Controller
public class CategorieController {
    CategorieService cService;
    ArticleVenduService aService;
    public CategorieController(CategorieService cService,  ArticleVenduService aService) {
        this.aService = aService;
        this.cService = cService;
    }


//    @GetMapping("/categorie")
//    public  String displayListCategorie(Model model){
//        List<Categorie> categories = cService.findAll();
//        model.addAttribute("listCategories",categories);
//        System.out.println(categories);
//        return "/detail_enchere";
//    }


    @GetMapping("/categorieArticle")
    public  String displayListArticleByCategorie(@RequestParam("idCategorie") long noCategorie, Model model){
        List<ArticleVendu> ArticleCategories = aService.getLstArticleVendusByCategorie(noCategorie);
        model.addAttribute("listArticleCategories",ArticleCategories);
        model.addAttribute("categorie",cService.findById(noCategorie));
        return "article_filtered_categorie";
    }







}
