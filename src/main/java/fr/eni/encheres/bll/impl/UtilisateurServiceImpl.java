package fr.eni.encheres.bll.impl;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.dto.UtilisateurDTO;
import fr.eni.encheres.exception.BusinessCode;
import fr.eni.encheres.exception.BusinessException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

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

        isValid &= emailValidate(utilisateurDTO.getEmail(), be);
        isValid &= pseudoValidate(utilisateurDTO.getPseudo(), be);
        isValid &= passwordValidate(utilisateurDTO, be);

        if (isValid) {
            Utilisateur utilisateur = utilisateurDTO.toUtilisateur();

            utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
            dao.createUtilisateur(utilisateur);
        } else {
            throw be;
        }
    }

    @Override
    public void updateUtilisateur(Utilisateur utilisateur) {
        dao.updateUtilisateur(utilisateur);
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
            System.out.println(pseudo);
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
    public int checkPseudo(String pseudo) {
        try {
            return dao.checkUtilisateurByPseudo(pseudo);
        } catch (DataAccessException e) {
            throw new BusinessException(BusinessCode.DB_PSEUDO_VERIFICATION_ERROR);
        }
    }

    @Override
    public int checkEmail(String email) {
        try {
            return dao.checkUtilisateurByEmail(email);
        } catch (DataAccessException e) {
            throw new BusinessException(BusinessCode.DB_EMAIL_VERIFICATION_ERROR);
        }
    }

    // VALIDATION UTILISATEUR
    private boolean emailValidate(String email, BusinessException be) {
        try {
            if (dao.checkUtilisateurByEmail(email) > 0) {
                be.addFieldError("email", "validation.email.unique");
                return false;
            }
        } catch (DataAccessException e) {
            be.add(BusinessCode.ERROR_TECHNIQUE_EMAIL_VALIDATION);
        }
        return true;
    }

    private boolean pseudoValidate(String pseudo, BusinessException be) {
        try {
            if (dao.checkUtilisateurByPseudo(pseudo) > 0) {
                be.addFieldError("pseudo", "validation.pseudo.unique");
                return false;
            }
        } catch (DataAccessException e) {
            be.add(BusinessCode.ERROR_TECHNIQUE_PSEUDO_VALIDATION);
            return false;
        }
        return true;
    }

    private boolean passwordValidate(UtilisateurDTO utilisateurDTO, BusinessException be) {
        if (!utilisateurDTO.getMotDePasse().equals(utilisateurDTO.getMotDePasseConfirm())) {
            be.addFieldError("motDePasseConfirm", "validation.pwd-confirm");
            return false;
        }
        return true;
    }
}
