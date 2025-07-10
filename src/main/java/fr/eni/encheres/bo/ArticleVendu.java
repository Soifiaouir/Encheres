package fr.eni.encheres.bo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArticleVendu {
    private long noArticle;
    private String nomArticle;
    private String description;
    private LocalDate dateDebutEncheres;
    private LocalDate dateFinEncheres;
    private int prixInitial;
    private int prixVente;
    private String etatVente;

    private Retrait lieuRetrait;

    private Utilisateur utilisateur;

    private Categorie categorie;

    private List<Enchere> lstEncheres = new ArrayList<>();

    public ArticleVendu() {
    }

    public ArticleVendu(long noArticle, String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int prixInitial, int prixVente, String etatVente, List<Enchere> lstEncheres) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
        this.lstEncheres = new ArrayList<>();
    }

    public ArticleVendu(String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int prixInitial, int prixVente, String etatVente, List<Enchere> lstEncheres) {
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
        this.lstEncheres = new ArrayList<>();
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

    public String getEtatVente() {
        return etatVente;
    }

    public void setEtatVente(String etatVente) {
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
