package fr.eni.encheres.dto;

import jakarta.validation.constraints.NotBlank;

public class PwdDTO {
    public interface UpdatePwdValidation {};

    private long noUtilisateur;

    @NotBlank(groups = UpdatePwdValidation.class)
    private String motDePasseActuel;

    @NotBlank(groups = UpdatePwdValidation.class)
    private String nouveauMotDePasse;

    @NotBlank(groups = UpdatePwdValidation.class)
    private String motDePasseConfirm;

    public String getMotDePasseConfirm() {
        return motDePasseConfirm;
    }

    public void setMotDePasseConfirm(String motDePasseConfirm) {
        this.motDePasseConfirm = motDePasseConfirm;
    }

    public String getNouveauMotDePasse() {
        return nouveauMotDePasse;
    }

    public void setNouveauMotDePasse(String nouveauMotDePasse) {
        this.nouveauMotDePasse = nouveauMotDePasse;
    }

    public String getMotDePasseActuel() {
        return motDePasseActuel;
    }

    public void setMotDePasseActuel(String motDePasseActuel) {
        this.motDePasseActuel = motDePasseActuel;
    }

    public long getNoUtilisateur() {
        return noUtilisateur;
    }

    public void setNoUtilisateur(long noUtilisateur) {
        this.noUtilisateur = noUtilisateur;
    }
}
