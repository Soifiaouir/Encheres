package fr.eni.encheres.ihm.security;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dto.UtilisateurDTO;
import fr.eni.encheres.exception.BusinessException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;

@Controller
@SessionAttributes({"userSession"})
public class AuthController {

    private final UtilisateurService service;

    public AuthController(UtilisateurService service) {
        this.service = service;
    }

    @GetMapping({"/", "accueil"})
    public String index(@ModelAttribute("userSession") Utilisateur userSession) {
        return "index";
    }

    @ModelAttribute("userSession")
    public Utilisateur userSession() {
        //Utilisateur utilisateur = service.findUtilisateurById(1);
        Utilisateur utilisateur = new Utilisateur();

        return utilisateur;
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
            userSession.setNoUtilisateur(userTemp.getNoUtilisateur());
            userSession.setPseudo(userTemp.getPseudo());
            userSession.setNom(userTemp.getNom());
            userSession.setPrenom(userTemp.getPrenom());
            userSession.setEmail(userTemp.getEmail());
            userSession.setTelephone(userTemp.getTelephone());
            userSession.setRue(userTemp.getRue());
            userSession.setCodePostal(userTemp.getCodePostal());
            userSession.setVille(userTemp.getVille());
            userSession.setCredit(userTemp.getCredit());
            userSession.setAdministrateur(userTemp.isAdministrateur());
        } else {
            userSession.setNoUtilisateur(0);
        }
        return "redirect:/";
    }

    @GetMapping("/signin")
    public String signin(Model model) {
        model.addAttribute("utilisateurDTO", new UtilisateurDTO());
        return "signin";
    }

    @PostMapping("/signin")
    public String newUtilisateur(@Valid @ModelAttribute("utilisateurDTO") UtilisateurDTO utilisateurDTO,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "signin";
        }
        try {
            service.createUtilisateur(utilisateurDTO);
            return "redirect:/login?signin=success";
        } catch (BusinessException e) {
            // Ajout des erreurs sur les champs
            e.getFieldErrors().forEach(bindingResult::rejectValue);
        }
        return "signin";
    }
}
