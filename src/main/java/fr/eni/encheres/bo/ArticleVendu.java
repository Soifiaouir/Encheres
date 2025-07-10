package fr.eni.encheres.bo;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ArticleVendu {
    private long noArticle;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9\\-]+$")
    private String nomArticle;

    @NotBlank
    private String description;

    @FutureOrPresent
    private LocalDate dateDebutEncheres;

    @Future
    private LocalDate dateFinEncheres;

    @Positive
    private int prixInitial;
    private int prixVente;
    private Integer etatVente;

    private Retrait lieuRetrait;

    private Utilisateur utilisateur;

    private Categorie categorie;

    private List<Enchere> lstEncheres = new ArrayList<>();

    public ArticleVendu() {
    }

    public ArticleVendu(long noArticle, String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int prixInitial, int prixVente, Integer etatVente, Retrait lieuRetrait, Utilisateur utilisateur, Categorie categorie, List<Enchere> lstEncheres) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
        this.lieuRetrait = lieuRetrait;
        this.utilisateur = utilisateur;
        this.categorie = categorie;
        this.lstEncheres = lstEncheres;
    }

    public ArticleVendu(String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int prixInitial, int prixVente, Integer etatVente, Retrait lieuRetrait, Utilisateur utilisateur, Categorie categorie, List<Enchere> lstEncheres) {
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
        this.lieuRetrait = lieuRetrait;
        this.utilisateur = utilisateur;
        this.categorie = categorie;
        this.lstEncheres = lstEncheres;
    }

    public long getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(long noArticle) {
        this.noArticle = noArticle;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateDebutEncheresFormatee() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.dateDebutEncheres.format(formatter);
    }

    public String getDateFinEncheresFormatee() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.dateFinEncheres.format(formatter);
    }

    public LocalDate getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public LocalDate getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(LocalDate dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public Long getCalendrierEnchere(LocalDate dateDebutEnchere, LocalDate dateFinEnchere) {
        LocalDate today = LocalDate.now();
        if (dateDebutEnchere.isAfter(today)){
            return ChronoUnit.DAYS.between(today, dateDebutEnchere);
        }
        else {
            return ChronoUnit.DAYS.between(today, dateFinEnchere);
        }
    }

    public int getPrixInitial() {
        return prixInitial;
    }

    public void setPrixInitial(int prixInitial) {
        this.prixInitial = prixInitial;
    }

    public int getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(int prixVente) {
        this.prixVente = prixVente;
    }

    public Retrait getLieuRetrait() {
        return lieuRetrait;
    }

    public void setLieuRetrait(Retrait lieuRetrait) {
        this.lieuRetrait = lieuRetrait;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<Enchere> getLstEncheres() {
        return lstEncheres;
    }

    public void setLstEncheres(List<Enchere> lstEncheres) {
        this.lstEncheres = lstEncheres;
    }

    public Integer getEtatVente() {
        return etatVente;
    }

    public void setEtatVente(Integer etatVente) {
        this.etatVente = etatVente;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ArticleVendu{");
        sb.append("noArticle=").append(noArticle);
        sb.append(", nomArticle='").append(nomArticle).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", dateDebutEncheres=").append(dateDebutEncheres);
        sb.append(", dateFinEncheres=").append(dateFinEncheres);
        sb.append(", prixInitial=").append(prixInitial);
        sb.append(", prixVente=").append(prixVente);
        sb.append(", etatVente='").append(etatVente).append('\'');
        sb.append(", lieuRetrait=").append(lieuRetrait);
        sb.append(", utilisateur=").append(utilisateur);
        sb.append(", categorie=").append(categorie);
        sb.append(", lstEncheres=").append(lstEncheres);
        sb.append('}');
        return sb.toString();
    }

    public Enchere getDerniereEnchere(){
        if (lstEncheres == null || lstEncheres.isEmpty()) {
            return null;
        }
        return lstEncheres.get(lstEncheres.size()-1);
    }

}
