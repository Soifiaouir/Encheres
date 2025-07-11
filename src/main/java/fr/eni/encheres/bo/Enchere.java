package fr.eni.encheres.bo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Enchere {

    private long noEnchere;

    private LocalDate dateEnchere;

    @NotNull(message = "Le montant de l'enchère est obligatoire")
    @Min(value = 1, message = "Le montant doit être supérieur à 0")
    private Integer montantEnchere;

    private Utilisateur encherisseur;

    private ArticleVendu articleConcerne;

    public Enchere() {
    }

    public Enchere(LocalDate dateEnchere, Integer montantEnchere) {
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
    }

    public Enchere(long noEnchere, LocalDate dateEnchere, Integer montantEnchere) {
        this.noEnchere = noEnchere;
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
    }

    public long getNoEnchere() {
        return noEnchere;
    }

    public void setNoEnchere(long noEnchere) {
        this.noEnchere = noEnchere;
    }

    public LocalDate getDateEnchere() {
        return dateEnchere;
    }


    /* Method to format the date in french view */
    public String getDateFormatee() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.dateEnchere.format(formatter);
    }


    public void setDateEnchere(LocalDate dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public Integer getMontantEnchere() {
        return montantEnchere;
    }

    public void setMontantEnchere(Integer montantEnchere) {
        this.montantEnchere = montantEnchere;
    }

    public Utilisateur getEncherisseur() {
        return encherisseur;
    }

    public void setEncherisseur(Utilisateur encherisseur) {
        this.encherisseur = encherisseur;
    }

    public ArticleVendu getArticleConcerne() {
        return articleConcerne;
    }

    public void setArticleConcerne(ArticleVendu articleConcerne) {
        this.articleConcerne = articleConcerne;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Enchere{");
        sb.append("noEnchere=").append(noEnchere);
        sb.append(", dateEnchere=").append(dateEnchere);
        sb.append(", montantEnchere=").append(montantEnchere);
        sb.append(", encherisseur=").append(encherisseur);
        sb.append(", articleConcerne=").append(articleConcerne);
        sb.append('}');
        return sb.toString();
    }
}
