package fr.eni.encheres.exception;

public class BusinessCode {

    public static final String DB_UTILISATEUR_INCONNU = "db.utilisateur.inconnu";
    public static final String DB_PSEUDO_VERIFICATION_ERROR = "db.pseudo.verification.error";
    public static final String DB_EMAIL_VERIFICATION_ERROR = "db.email.verification.error";

    public static final String ERROR_TECHNIQUE_EMAIL_VALIDATION = "service.validation.email.error";
    public static final String ERROR_TECHNIQUE_PSEUDO_VALIDATION = "service.validation.pseudo.error";

    public  static final String ERREUR_DATE_ENCHERE = "service.date.enchere.error";
    public  static final String ERREUR_MONTANT_ENCHERE = "service.montant.enchere.error";
    public static final String ERROR_UTILISATEUR_INEXISTANT = "service.utilisateur.enchere.error";
    public static final String ERREUR_ARTICLE_INEXISTANT = "service.article.enchere.error";
    public static final String BLL_ENCHERE_CREATION_ECHOUEE = "service.enchere;.creat.error";

}
