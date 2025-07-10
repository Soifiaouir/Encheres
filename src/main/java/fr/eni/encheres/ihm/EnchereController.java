package fr.eni.encheres.ihm;

import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/encheres")
@Controller
public class EnchereController {
    EnchereService eService;
    UtilisateurService uService;
    ArticleVenduService aService;

    public EnchereController(EnchereService eService, UtilisateurService uService, ArticleVenduService aService) {
        this.eService = eService;
        this.uService = uService;
        this.aService = aService;
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
