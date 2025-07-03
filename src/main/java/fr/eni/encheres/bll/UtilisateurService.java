package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurService {

    public void createUtilisateur(Utilisateur utilisateur);
    public void updateUtilisateur(Utilisateur utilisateur);
    public void deleteUtilisateur(long id);
    public Utilisateur findUtilisateurById(long id);
    public Utilisateur findUtilisateurByPseudo(String pseudo);
    public List<Utilisateur> findAllUtilisateurs();
    public int checkPseudo(String pseudo);
    public int checkEmail(String email);
}
