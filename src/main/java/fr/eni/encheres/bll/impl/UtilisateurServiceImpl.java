package fr.eni.encheres.bll.impl;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.dto.PwdDTO;
import fr.eni.encheres.dto.UtilisateurDTO;
import fr.eni.encheres.exception.BusinessCode;
import fr.eni.encheres.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private static final Logger log = LoggerFactory.getLogger(UtilisateurServiceImpl.class);
    private final UtilisateurDAO dao;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO, PasswordEncoder passwordEncoder) {
        this.dao = utilisateurDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUtilisateur(UtilisateurDTO utilisateurDTO) {
        BusinessException be = new BusinessException();
        boolean isValid = true;

        isValid &= emailValidate(utilisateurDTO, be);
        isValid &= pseudoValidate(utilisateurDTO, be);
        isValid &= passwordValidate(utilisateurDTO.getMotDePasse(), utilisateurDTO.getMotDePasseConfirm(), be);

        if (isValid) {
            Utilisateur utilisateur = utilisateurDTO.toUtilisateur();

            utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
            dao.createUtilisateur(utilisateur);
        } else {
            throw be;
        }
    }

    @Override
    public Utilisateur updateUtilisateur(UtilisateurDTO utilisateurDTO) {
        BusinessException be = new BusinessException();
        boolean isValid = true;

        if (utilisateurDTO.getEmail() != null)
            isValid &= emailValidate(utilisateurDTO, be);
        if (utilisateurDTO.getPseudo() != null)
            isValid &= pseudoValidate(utilisateurDTO, be);

        if (isValid) {
            Utilisateur userTemps = dao.readUtilisateurById(utilisateurDTO.getNoUtilisateur());

            Utilisateur utilisateur = utilisateurDTO.merge(userTemps).toUtilisateur();

            dao.updateUtilisateur(utilisateur);
            return utilisateur;
        } else {
            throw be;
        }
    }

    @Override
    public void updatePwd(PwdDTO pwdDTO) {
        BusinessException be = new BusinessException();
        boolean isValid = true;

        isValid &= passwordCheck(pwdDTO, be);
        isValid &= passwordValidate(pwdDTO.getNouveauMotDePasse(), pwdDTO.getMotDePasseConfirm(), be);

        if (isValid) {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNoUtilisateur(pwdDTO.getNoUtilisateur());
            utilisateur.setMotDePasse(passwordEncoder.encode(pwdDTO.getNouveauMotDePasse()));

            dao.updatePwd(utilisateur);
        } else {
            throw be;
        }
    }

    @Override
    public void deleteUtilisateur(long id) {
        dao.deleteUtilisateur(id);
    }

    @Override
    public Utilisateur findUtilisateurById(long id) {
        try {
            return dao.readUtilisateurById(id);
        } catch (DataAccessException e) {
            throw new BusinessException(BusinessCode.DB_UTILISATEUR_INCONNU);
        }
    }

    @Override
    public Utilisateur findUtilisateurByPseudo(String pseudo) {
        try {
            return dao.readUtilisateurByPseudo(pseudo);
        } catch (DataAccessException e) {
            throw new BusinessException(BusinessCode.DB_UTILISATEUR_INCONNU);
        }
    }

    @Override
    public List<Utilisateur> findAllUtilisateurs() {
        try {
            return dao.readAllUtilisateurs();
        } catch (DataAccessException e) {
            throw new BusinessException(BusinessCode.DB_UTILISATEUR_INCONNU);
        }
    }

    @Override
    public void addCredit(UtilisateurDTO utilisateurDTO, int credit) {
        if(credit > 0){
            utilisateurDTO.setCredit(utilisateurDTO.getCredit() + credit);
        }

        try {
            dao.addCredit(utilisateurDTO);
        } catch (DataAccessException e) {
            throw new BusinessException(BusinessCode.DB_UTILISATEUR_INCONNU);
        }
    }

    @Override
    public void enableAccount(String pseudo) {
        try {
            Utilisateur utilisateur = dao.readUtilisateurByPseudo(pseudo);

            dao.setActive(utilisateur);
        } catch (DataAccessException e) {
            throw new BusinessException(BusinessCode.DB_UTILISATEUR_INCONNU);
        }
    }

    @Override
    public void disableAccount(String pseudo) {
        try {
            Utilisateur utilisateur = dao.readUtilisateurByPseudo(pseudo);

            dao.setInactive(utilisateur);
        } catch (DataAccessException e) {
            throw new BusinessException(BusinessCode.DB_UTILISATEUR_INCONNU);
        }
    }


    // VALIDATION UTILISATEUR
    private boolean emailValidate(UtilisateurDTO utilisateurDTO, BusinessException be) {
        try {
            boolean count = (utilisateurDTO.getNoUtilisateur() > 0)
                    ? dao.checkEmailExistForOther(utilisateurDTO.getEmail(), utilisateurDTO.getNoUtilisateur()) > 0
                    : dao.checkEmailExist(utilisateurDTO.getEmail()) > 0;

            if (count) {
                be.addFieldError("email", "validation.email.unique");
                return false;
            }
        } catch (DataAccessException e) {
            be.add(BusinessCode.ERROR_TECHNIQUE_EMAIL_VALIDATION);
        }
        return true;
    }
    private boolean pseudoValidate(UtilisateurDTO utilisateurDTO, BusinessException be) {
        try {
            log.info("Début de la validation du pseudo : {}", utilisateurDTO.getPseudo());
            log.info("id : {}", utilisateurDTO.getNoUtilisateur());
            boolean count = (utilisateurDTO.getNoUtilisateur() > 0)
                    ? dao.checkPseudoExistForOther(utilisateurDTO.getPseudo(), utilisateurDTO.getNoUtilisateur()) > 0
                    : dao.checkPseudoExist(utilisateurDTO.getPseudo()) > 0;

            log.info("Vérification du pseudo {} : {}", utilisateurDTO.getPseudo(), count ? "existe déjà" : "disponible");

            if (count) {
                be.addFieldError("pseudo", "validation.pseudo.unique");
                log.info("Le pseudo est déjà pris. Ajout d'une erreur de validation.");
                return false;
            }
        } catch (DataAccessException e) {
            log.info("Erreur lors de la validation du pseudo : {}", e.getMessage(), e);
            be.add(BusinessCode.ERROR_TECHNIQUE_PSEUDO_VALIDATION);
        }

        log.info("Validation du pseudo terminée, aucune erreur.");
        return true;
    }


    private boolean passwordValidate(String pwd, String confirm, BusinessException be) {
        if (!pwd.equals(confirm)) {
            be.addFieldError("motDePasseConfirm", "validation.pwd-confirm");
            return false;
        }
        return true;
    }

    private boolean passwordCheck(PwdDTO pwdDTO, BusinessException be) {
        try {
            String pwdHashed = dao.checkPwd(pwdDTO.getNoUtilisateur());

            if(pwdHashed == null || !passwordEncoder.matches(pwdDTO.getMotDePasseActuel(), pwdHashed)) {
                be.addFieldError("motDePasseActuel", "validation.pwd-wrong");
                return false;
            }
        } catch (DataAccessException e) {
            throw new BusinessException(BusinessCode.DB_UTILISATEUR_INCONNU);
        }
       return true;
    }
}
