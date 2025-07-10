package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dto.UtilisateurDTO;

import java.util.List;

public interface UtilisateurDAO {

    // CRUD
    public void createUtilisateur(Utilisateur utilisateur);
    public void updateUtilisateur(Utilisateur utilisateur);
    public void updatePwd(Utilisateur utilisateur);
    public Utilisateur readUtilisateurById(long id);
    public Utilisateur readUtilisateurByPseudo(String pseudo);
    public List<Utilisateur> readAllUtilisateurs();
    public void deleteUtilisateur(long id);

    // SIGN CHECK
    public int checkPseudoExist(String pseudo);
    public int checkEmailExist(String email);
    public int checkPseudoExistForOther(String pseudo, long Id);
    public int checkEmailExistForOther(String email, long id);

    public String checkPwd(long id);
    public void addCredit(UtilisateurDTO utilisateurDTO);
    public void setActive(Utilisateur utilisateur);
    public void setInactive(Utilisateur utilisateur);

}
