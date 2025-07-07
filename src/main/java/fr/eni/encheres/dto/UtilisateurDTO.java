package fr.eni.encheres.dto;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UtilisateurDTO {

    private long noUtilisateur;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String pseudo;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @NotBlank
    private String email;

    @NotBlank
    private String telephone;

    @NotBlank
    private String rue;

    @NotBlank
    private String codePostal;

    @NotBlank
    private String ville;

    @NotBlank
    private String motDePasse;

    @NotBlank
    private String motDePasseConfirm;

    private int credit = 100;
    private boolean administrateur = false;

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getMotDePasseConfirm() {
        return motDePasseConfirm;
    }

    public void setMotDePasseConfirm(String motDePasseConfirm) {
        this.motDePasseConfirm = motDePasseConfirm;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(boolean administrateur) {
        this.administrateur = administrateur;
    }

    public Utilisateur toUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setNoUtilisateur(this.noUtilisateur);
        utilisateur.setPseudo(this.pseudo);
        utilisateur.setNom(this.nom);
        utilisateur.setPrenom(this.prenom);
        utilisateur.setEmail(this.email);
        utilisateur.setTelephone(this.telephone);
        utilisateur.setRue(this.rue);
        utilisateur.setCodePostal(this.codePostal);
        utilisateur.setVille(this.ville);
        utilisateur.setMotDePasse(this.motDePasse);
        utilisateur.setCredit(this.credit);
        utilisateur.setAdministrateur(this.administrateur);

        return utilisateur;
    }
}
