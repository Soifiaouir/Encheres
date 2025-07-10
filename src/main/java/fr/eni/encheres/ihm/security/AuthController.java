package fr.eni.encheres.ihm.security;

import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dto.UtilisateurDTO;
import fr.eni.encheres.exception.BusinessException;
import fr.eni.encheres.ihm.UtilisateurController;
import fr.eni.encheres.util.SessionUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@SessionAttributes({"userSession"})
public class AuthController {

    private final UtilisateurService service;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final EnchereService eService;
    private final CategorieService cService;

    public AuthController(UtilisateurService service, EnchereService eService, CategorieService cService) {
        this.service = service;
        this.eService = eService;
        this.cService = cService;
    }

    @GetMapping({"/", "accueil"})
    public String index(@ModelAttribute("userSession") Utilisateur userSession, Model model) {
        List<Enchere> encheres = eService.findListEnchere();
        List<Categorie> categories = cService.findAll();
        model.addAttribute("listCategories",categories);
        model.addAttribute("encherelst", encheres);
        return "index";
    }

    @ModelAttribute("userSession")
    public Utilisateur userSession() {
        return new Utilisateur();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/session")
    public String newSession(@ModelAttribute("userSession") Utilisateur userSession,
                             Principal principal) {
        String pseudo = principal.getName();
        System.out.println("pseudo: " + pseudo);

        Utilisateur userTemp = service.findUtilisateurByPseudo(pseudo);
        if (userTemp != null) {
            SessionUtils.setSession(userSession, userTemp);

            log.info("SESSION STARTED - {}", userSession.toString());
        } else {
            userSession.setNoUtilisateur(0);
            log.warn("SESSION FAILED");
        }
        return "redirect:/";
    }

    @GetMapping("/session/update")
    public String updateSession(@ModelAttribute("userSession") Utilisateur userSession, @RequestParam(name = "id") long id) {
        Utilisateur userTemp = service.findUtilisateurById(id);

        if (userTemp != null) {
            log.info("SESSION UPDATED - {}", userSession.toString());
            SessionUtils.setSession(userSession, userTemp);
        }

        return "redirect:/profil?user=" + userSession.getNoUtilisateur() + "&edit=success";
    }
    @GetMapping("/signin")
    public String signin(Model model) {
        model.addAttribute("utilisateurDTO", new UtilisateurDTO());
        return "signin";
    }

    @PostMapping("/signin")
    public String newUtilisateur(@Validated(UtilisateurDTO.SignInValidation.class) @ModelAttribute("utilisateurDTO") UtilisateurDTO utilisateurDTO,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.warn(bindingResult.getAllErrors().toString());
            return "signin";
        }
        try {
            service.createUtilisateur(utilisateurDTO);
            return "redirect:/login?signin=success";
        } catch (BusinessException e) {
            // Ajout des erreurs sur les champs
            log.warn(e.getMessage());
            e.getFieldErrors().forEach(bindingResult::rejectValue);
        }
        return "signin";
    }

    @GetMapping("/reactiver-compte")
    public String reactiverCompte(HttpSession session, Model model) {
        String pseudo = (String) session.getAttribute("pseudoInactif");
        log.info("REACTIVER COMPTE - {}", pseudo);

        if (pseudo != null) {
            model.addAttribute("pseudoInactif", pseudo); // ici tu passes bien pseudoInactif
        }

        return "reactiver-compte";
    }

    @GetMapping("/reactiver-compte/true")
    public String activationCompte(HttpSession session) {
        String pseudo = (String) session.getAttribute("pseudoInactif");

        service.enableAccount(pseudo);
        return "redirect:/login?account=active";
    }
}
