package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurDAO {

    // CRUD
    public void createUtilisateur(Utilisateur utilisateur);
    public void updateUtilisateur(Utilisateur utilisateur);
    public Utilisateur readUtilisateurById(long id);
    public Utilisateur readUtilisateurByPseudo(String pseudo);
    public List<Utilisateur> readAllUtilisateurs();
    public void deleteUtilisateur(long id);

    // SIGN CHECK
    public int checkUtilisateurByPseudo(String pseudo);
    public int checkUtilisateurByEmail(String email);
}
