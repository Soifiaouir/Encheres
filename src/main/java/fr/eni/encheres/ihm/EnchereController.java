package fr.eni.encheres.ihm;

import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SessionAttributes("userSession")
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

    public String listEncheres(Model model,
                               @RequestParam (value = "noArticle", required = false) Long noArticle,
                               @ModelAttribute("userSession") Utilisateur user) {

    List<Enchere> encheresU = eService.findByUser(user.getNoUtilisateur());
    List<Enchere> encheres;

    if (noArticle != null) {
        encheres = eService.findByArticle(noArticle);
    } else {
        encheres = eService.findListEnchere();
    }

    model.addAttribute("encherelst", encheres);
    model.addAttribute("userEnchere", encheresU);

        System.out.println(encheres);

        return "lstenchere";
    }

    /**
     * Methode to give the information to the form that will create a bid
     * @param noArticle
     * @param model
     * @return
     */
    @GetMapping("/bid")
    public String createBid(@RequestParam ("noArticle") Long noArticle,
                            @ModelAttribute("userSession")  Utilisateur userSession,
                            @ModelAttribute("enchere") Enchere enchere,
                            Model model){


        if (userSession != null && userSession.getNoUtilisateur() >= 1) {

            ArticleVendu article = aService.getArticleVenduByNoArticle(noArticle);
            model.addAttribute("localDateTime", LocalDateTime.now());
            model.addAttribute("article", article);
            model.addAttribute("noArticle", article.getNoArticle());
            model.addAttribute("enchere", new Enchere());
            System.out.println(noArticle);
            return "bidding";
        }else {
            return "redirect:/list_enchere";
        }
    }

    /**
     * Methode that will get the information from the form and give them to the data base
     * @param enchere
     * @param noArticle
     * @return
     */
    @PostMapping("/bid")
    public String createEnchere(@Valid
                                @ModelAttribute("enchere") Enchere enchere,
                                BindingResult bindingResult,
                                @RequestParam (value = "noArticle", required = true) Long noArticle,
                                @ModelAttribute("userSession")   Utilisateur userSession,
                                Model model) {
        if (userSession != null && userSession.getNoUtilisateur() >= 1) {
            ArticleVendu article = aService.getArticleVenduByNoArticle(noArticle);
            model.addAttribute("article", article);
            model.addAttribute("noArticle", noArticle);

            enchere.setArticleConcerne(article);
            enchere.setEncherisseur(userSession);
            enchere.setDateEnchere(LocalDate.now());
            try {

                eService.createEnchere(enchere, noArticle);


                return "redirect:/list_enchere";
            } catch (BusinessException e) {

                e.getClefsExternalisations().forEach(key -> {
                    ObjectError error = new ObjectError("globalError", key);
                    bindingResult.addError(error);
                });

                return "bidding";
            }
        } else {
            return "lstenchere";
        }

    }





}
