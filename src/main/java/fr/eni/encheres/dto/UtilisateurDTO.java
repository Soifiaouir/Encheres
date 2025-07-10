package fr.eni.encheres.dto;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UtilisateurDTO {

    public interface SignInValidation extends
            UpdateInfoValidation,
            UpdateAdresseValidation,
            UpdatePwdValidation {}

    public interface UpdateInfoValidation {};
    public interface UpdateAdresseValidation {};
    public interface UpdatePwdValidation {};

    private long noUtilisateur;

    @NotBlank(groups = UpdateInfoValidation.class)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", groups = UpdateInfoValidation.class)
    private String pseudo;

    @NotBlank(groups = UpdateInfoValidation.class)
    private String nom;

    @NotBlank(groups = UpdateInfoValidation.class)
    private String prenom;

    @NotBlank(groups = UpdateInfoValidation.class)
    private String email;

    @NotBlank(groups = UpdateInfoValidation.class)
    private String telephone;

    @NotBlank(groups = UpdateAdresseValidation.class)
    private String rue;

    @NotBlank(groups = UpdateAdresseValidation.class)
    private String codePostal;

    @NotBlank(groups = UpdateAdresseValidation.class)
    private String ville;

    @NotBlank(groups = UpdatePwdValidation.class)
    private String motDePasse;

    @NotBlank(groups = UpdatePwdValidation.class)
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

    public long getNoUtilisateur() {
        return noUtilisateur;
    }

    public void setNoUtilisateur(long noUtilisateur) {
        this.noUtilisateur = noUtilisateur;
    }

    public UtilisateurDTO() {};

    public UtilisateurDTO(Utilisateur utilisateur) {
        this.setNoUtilisateur(utilisateur.getNoUtilisateur());
        this.setPseudo(utilisateur.getPseudo());
        this.setNom(utilisateur.getNom());
        this.setPrenom(utilisateur.getPrenom());
        this.setEmail(utilisateur.getEmail());
        this.setTelephone(utilisateur.getTelephone());
        this.setRue(utilisateur.getRue());
        this.setCodePostal(utilisateur.getCodePostal());
        this.setVille(utilisateur.getVille());
        this.setMotDePasse(utilisateur.getMotDePasse());
        this.setCredit(utilisateur.getCredit());
        this.setAdministrateur(utilisateur.isAdministrateur());
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

    public UtilisateurDTO merge(Utilisateur utilisateur) {
        if(this.noUtilisateur == 0) this.setNoUtilisateur(utilisateur.getNoUtilisateur());
        if(this.pseudo == null) this.setPseudo(utilisateur.getPseudo());
        if(this.nom == null) this.setNom(utilisateur.getNom());
        if(this.prenom == null) this.setPrenom(utilisateur.getPrenom());
        if(this.email == null) this.setEmail(utilisateur.getEmail());
        if(this.telephone == null) this.setTelephone(utilisateur.getTelephone());
        if(this.rue == null) this.setRue(utilisateur.getRue());
        if(this.codePostal == null) this.setCodePostal(utilisateur.getCodePostal());
        if(this.ville == null) this.setVille(utilisateur.getVille());
        if(this.credit != utilisateur.getCredit()) this.setCredit(utilisateur.getCredit());
        if(this.administrateur != utilisateur.isAdministrateur()) this.setAdministrateur(utilisateur.isAdministrateur());

        return this;
    }
}
