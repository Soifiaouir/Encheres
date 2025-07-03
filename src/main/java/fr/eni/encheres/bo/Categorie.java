package fr.eni.encheres.bo;

import java.util.List;

public class Categorie {

    private long noCategorie;
    private String libelle;

    private List<ArticleVendu> lstArticles;

    public Categorie() {
    }

    public Categorie(String libelle) {
        this.libelle = libelle;
    }

    public Categorie(String libelle, long noCategorie) {
        this.libelle = libelle;
        this.noCategorie = noCategorie;
    }

    public long getNoCategorie() {
        return noCategorie;
    }

    public void setNoCategorie(long noCategorie) {
        this.noCategorie = noCategorie;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<ArticleVendu> getLstArticles() {
        return lstArticles;
    }

    public void setLstArticles(List<ArticleVendu> lstArticles) {
        this.lstArticles = lstArticles;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Categorie{");
        sb.append("noCategorie=").append(noCategorie);
        sb.append(", libelle='").append(libelle).append('\'');
        sb.append(", lstArticles=").append(lstArticles);
        sb.append('}');
        return sb.toString();
    }
}
