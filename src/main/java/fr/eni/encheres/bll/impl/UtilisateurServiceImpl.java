package fr.eni.encheres.bll.impl;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exception.BusinessCode;
import fr.eni.encheres.exception.BusinessException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurDAO dao;

    public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO) {
        this.dao = utilisateurDAO;
    }

    @Override
    public void createUtilisateur(Utilisateur utilisateur) {
        BusinessException be = new BusinessException();
        boolean isValid = true;

        isValid &= emailValidate(utilisateur.getEmail(), be);
        isValid &= pseudoValidate(utilisateur.getPseudo(), be);

        if (isValid) {
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
       }  catch (DataAccessException e) {
           throw new BusinessException(BusinessCode.DB_UTILISATEUR_INCONNU);
       }
    }

    @Override
    public Utilisateur findUtilisateurByPseudo(String pseudo) {
        try {
            System.out.println(pseudo);
            return dao.readUtilisateurByPseudo(pseudo);
        }  catch (DataAccessException e) {
            throw new BusinessException(BusinessCode.DB_UTILISATEUR_INCONNU);
        }
    }

    @Override
    public List<Utilisateur> findAllUtilisateurs() {
        try {
            return dao.readAllUtilisateurs();
        }  catch (DataAccessException e) {
            throw new BusinessException(BusinessCode.DB_UTILISATEUR_INCONNU);
        }
    }

    @Override
    public int checkPseudo(String pseudo) {
        try {
            return dao.checkUtilisateurByPseudo(pseudo);
        }  catch (DataAccessException e) {
            throw new BusinessException(BusinessCode.DB_PSEUDO_VERIFICATION_ERROR);
        }
    }

    @Override
    public int checkEmail(String email) {
        try {
            return dao.checkUtilisateurByEmail(email);
        }  catch (DataAccessException e) {
            throw new BusinessException(BusinessCode.DB_EMAIL_VERIFICATION_ERROR);
        }
    }

    // VALIDATION UTILISATEUR
    private boolean emailValidate(String email, BusinessException be) {
        try {
            int countEmail = dao.checkUtilisateurByEmail(email);

            if (countEmail > 0) {
                be.add(BusinessCode.VALIDATION_EMAIL_UNIQUE);
                return false;
            }
        } catch (DataAccessException e) {
            be.add(BusinessCode.VALIDATION_EMAIL_UNIQUE);
            return false;
        }
        return true;
    }
    private boolean pseudoValidate(String pseudo, BusinessException be) {
        try {
            int countPseudo = dao.checkUtilisateurByPseudo(pseudo);

            if (countPseudo > 0) {
                be.add(BusinessCode.VALIDATION_PSEUDO_UNIQUE);
                return false;
            }
        } catch (DataAccessException e) {
            be.add(BusinessCode.VALIDATION_PSEUDO_UNIQUE);
            return false;
        }
        return true;
    }
}
