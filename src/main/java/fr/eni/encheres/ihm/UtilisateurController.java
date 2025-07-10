package fr.eni.encheres.ihm;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dto.PwdDTO;
import fr.eni.encheres.dto.UtilisateurDTO;
import fr.eni.encheres.exception.BusinessException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@SessionAttributes("userSession")
public class UtilisateurController {

    private final UtilisateurService service;
    private static final Logger log = LoggerFactory.getLogger(UtilisateurController.class);

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.service = utilisateurService;
    }

    @ModelAttribute("userSession")
    public Utilisateur initSession() {
        Utilisateur userSession = new Utilisateur(); // avec id = 0 par défaut
        userSession.setNoUtilisateur(-1);

        return userSession;
    }

    @GetMapping("/credit")
    public String credit() {
        return "credit";
    }

    @GetMapping("/credit/buy")
    public String buyCredit(@RequestParam(name = "credit") int credit,
                            Principal principal) {
        try {
            UtilisateurDTO utilisateurDTO = buildUtilisateurDTOFromPrincipal(principal);
            service.addCredit(utilisateurDTO, credit);
            return "redirect:/session/update?id=" + utilisateurDTO.getNoUtilisateur();
        } catch (BusinessException be) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/profil")
    public String profil(@RequestParam(name = "user") long noUtilisateur, Model model) {
        try {
            Utilisateur utilisateur = service.findUtilisateurById(noUtilisateur);
            model.addAttribute("utilisateur", utilisateur);
            return "view-profil";
        } catch (BusinessException be) {
            log.warn(be.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable");
        }
    }

    @GetMapping("/profil/edit")
    public String edit(@ModelAttribute("userSession") Utilisateur userSession, Model model, Principal principal) {
        if (userSession == null || userSession.getNoUtilisateur() <= 0) {
            log.warn("ACCESS DENIED FOR VISITOR");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        try {
            UtilisateurDTO utilisateurDTO = buildUtilisateurDTOFromPrincipal(principal);

            model.addAttribute("utilisateurDTO", utilisateurDTO);
            model.addAttribute("pwdDTO", new PwdDTO());

            return "view-edit-profil";

        } catch (BusinessException be) {
            log.warn("Erreur lors de la récupération du profil : {}", be.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/profil/editInfo")
    public String editUserInfo(@Validated(UtilisateurDTO.UpdateInfoValidation.class)
                               @ModelAttribute("utilisateurDTO") UtilisateurDTO utilisateurDTO,
                               BindingResult bindingResult,
                               Principal principal,
                               Model model) {
        return handleProfilUpdate(utilisateurDTO, bindingResult, principal, model);
    }

    @PostMapping("/profil/editAdresse")
    public String editUserAdresse(@Validated(UtilisateurDTO.UpdateAdresseValidation.class)
                                  @ModelAttribute("utilisateurDTO") UtilisateurDTO utilisateurDTO,
                                  BindingResult bindingResult,
                                  Principal principal,
                                  Model model) {
        return handleProfilUpdate(utilisateurDTO, bindingResult, principal, model);
    }

    private String handleProfilUpdate(UtilisateurDTO utilisateurDTO,
                                      BindingResult bindingResult,
                                      Principal principal,
                                      Model model) {
        boolean pseudoUpdated = false;

        if (bindingResult.hasErrors()) {
            return "view-edit-profil";
        }
        try {
            String currentPseudo = principal.getName();
            Utilisateur userTemp = service.findUtilisateurByPseudo(currentPseudo);

            if (userTemp == null) {
                log.error("Utilisateur introuvable avec le pseudo : {}", currentPseudo);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable");
            }

            utilisateurDTO.setNoUtilisateur(userTemp.getNoUtilisateur());
            if (utilisateurDTO.getPseudo() != null) {
                pseudoUpdated = !utilisateurDTO.getPseudo().equals(userTemp.getPseudo());
            }
            Utilisateur updatedUser = service.updateUtilisateur(utilisateurDTO);

            if (updatedUser != null) {
                if (pseudoUpdated) {
                    SecurityContextHolder.clearContext(); // ← Supprime l'utilisateur courant
                    return "redirect:/login?update=pseudo";
                } else {
                    return "redirect:/session/update?id=" + updatedUser.getNoUtilisateur();
                }
            }
        } catch (BusinessException e) {
            e.getFieldErrors().forEach(bindingResult::rejectValue);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur base de données");
        }
        return "view-edit-profil";
    }


    @PostMapping("/profil/editPwd")
    public String editUserPwd(@Validated(PwdDTO.UpdatePwdValidation.class)
                              @ModelAttribute("pwdDTO") PwdDTO pwdDTO,
                              BindingResult bindingResult,
                              Principal principal,
                              Model model) {
        model.addAttribute("utilisateurDTO", buildUtilisateurDTOFromPrincipal(principal));

        if (bindingResult.hasErrors()) {
            return "view-edit-profil";
        }
        try {
            String currentPseudo = principal.getName();
            Utilisateur userTemp = service.findUtilisateurByPseudo(currentPseudo);

            if (userTemp == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable");
            }

            pwdDTO.setNoUtilisateur(userTemp.getNoUtilisateur());
            service.updatePwd(pwdDTO);
            return "redirect:/login?update=pwd";

        } catch (BusinessException e) {
            e.getFieldErrors().forEach(bindingResult::rejectValue);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur base de données");
        }
        return "view-edit-profil";
    }

    private UtilisateurDTO buildUtilisateurDTOFromPrincipal(Principal principal) {
        try {
            if (principal != null) {
                Utilisateur user = service.findUtilisateurByPseudo(principal.getName());
                return new UtilisateurDTO(user);
            }
        } catch (BusinessException e) {
            log.warn("Erreur lors de la reconstruction de l'utilisateurDTO : " + e.getMessage());
        }
        return new UtilisateurDTO(); // fallback vide
    }

    @GetMapping("profil/disable")
    public String disableAccount(Principal principal, Model model, HttpServletRequest request) {
        if (principal != null) {
            String pseudo = principal.getName();
            model.addAttribute("pseudoActif", pseudo);

            service.disableAccount(pseudo);
            SecurityContextHolder.clearContext();
            request.getSession().invalidate();
            return "desactiver-compte";
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
