package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dto.PwdDTO;
import fr.eni.encheres.dto.UtilisateurDTO;

import java.util.List;

public interface UtilisateurService {

    public void createUtilisateur(UtilisateurDTO utilisateurDTO);

    public Utilisateur updateUtilisateur(UtilisateurDTO utilisateurDTO);

    public void updatePwd(PwdDTO pwdDTO);

    public void deleteUtilisateur(long id);

    public Utilisateur findUtilisateurById(long id);

    public Utilisateur findUtilisateurByPseudo(String pseudo);

    public List<Utilisateur> findAllUtilisateurs();

    public void addCredit(UtilisateurDTO utilisateurDTO, int credit);

    public void enableAccount(String pseudo);

    public void disableAccount(String pseudo);
}
