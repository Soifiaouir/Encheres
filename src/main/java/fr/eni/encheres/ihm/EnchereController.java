package fr.eni.encheres.ihm;

import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import jdk.jshell.execution.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes("userSession")
public class EnchereController {
    private final EnchereService eService;
    private final UtilisateurService uService;
    private final ArticleVenduService aService;
    private final CategorieService cService;
    private static final Logger log = LoggerFactory.getLogger(EnchereController.class);


    public EnchereController(EnchereService eService, UtilisateurService uService, ArticleVenduService aService, CategorieService cService) {
        this.eService = eService;
        this.uService = uService;
        this.aService = aService;
        this.cService = cService;
    }




     @GetMapping({"/", "accueil"})
    public String index(Model model) {

        List<Categorie> lstCateg = cService.findAll();
        model.addAttribute("categories", lstCateg);
        log.info(lstCateg.toString());

        return "index";
    }
    @GetMapping("/list_enchere")
    public String listEncheres(Model model) {
        List<Enchere> encheres = eService.findListEnchere();
        List<Utilisateur> utilisateurs = uService.findAllUtilisateurs();
        model.addAttribute("encherelst", encheres);
        model.addAttribute("utilisateurslst", utilisateurs);
        System.out.println(encheres);
        return "detail_enchere";
    }

//    @PostMapping("/encherir")
//    public String addEnchere(@ModelAttribute("enchere") Enchere enchere, Model model) {
//
//    }

    @GetMapping("/listEnchereU")
    public String displayListEnchereUser(@RequestParam(name = "idUser" ) Long id, Model model) {

                List<Enchere> encheresU = eService.findByUser(id);
        model.addAttribute("userEnchere", encheresU);
        return "details_enchere_util";
    }

    @GetMapping("/listeArticleEnchere")
        public String displayListArticle( Model model) {
            List<ArticleVendu> articleVendus = aService.getLstArticleVendus();
            model.addAttribute("aVendus", articleVendus);
        return "enchere_list_article";
    }


}
