package fr.eni.encheres.util;

import fr.eni.encheres.bo.Utilisateur;

public final class SessionUtils {

    private SessionUtils() {}

    public static void setSession(Utilisateur userSession, Utilisateur utilisateur) {
        userSession.setNoUtilisateur(utilisateur.getNoUtilisateur());
        userSession.setPseudo(utilisateur.getPseudo());
        userSession.setNom(utilisateur.getNom());
        userSession.setPrenom(utilisateur.getPrenom());
        userSession.setEmail(utilisateur.getEmail());
        userSession.setTelephone(utilisateur.getTelephone());
        userSession.setRue(utilisateur.getRue());
        userSession.setCodePostal(utilisateur.getCodePostal());
        userSession.setVille(utilisateur.getVille());
        userSession.setCredit(utilisateur.getCredit());
        userSession.setAdministrateur(utilisateur.isAdministrateur());
    }
}
