package fr.eni.encheres.bo;

public class Retrait {

    private String rue;
    private String codePostal;
    private String ville;

    private ArticleVendu articleVendu;

    public Retrait() {
    }

    public Retrait(String rue, String codePostal, String ville, ArticleVendu articleVendu) {
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.articleVendu = articleVendu;
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

    public ArticleVendu getArticleVendu() {
        return articleVendu;
    }

    public void setArticleVendu(ArticleVendu articleVendu) {
        this.articleVendu = articleVendu;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Retrait{");
        sb.append("rue='").append(rue).append('\'');
        sb.append(", codePostal='").append(codePostal).append('\'');
        sb.append(", ville='").append(ville).append('\'');
        sb.append(", articleVendu=").append(articleVendu);
        sb.append('}');
        return sb.toString();
    }
}
