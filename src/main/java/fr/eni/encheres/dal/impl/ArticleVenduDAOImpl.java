package fr.eni.encheres.dal.impl;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleVenduDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class ArticleVenduDAOImpl implements ArticleVenduDAO {


    private final String FIND_BY_NUMBER = "SELECT a.no_article, a. nom_article, a.description, a.date_debut_encheres, " +
             "a.date_fin_encheres, a.prix_initial, a.prix_vente, a.etat_vente, a.no_categorie, " +
             "c.no_categorie, c.libelle, u.no_utilisateur, u.nom, u.prenom, u.pseudo, u.rue, u.code_postal, u.ville, r.rue, r.code_postal, r.ville " +
             "FROM articles_vendus a LEFT JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur " +
             "LEFT JOIN CATEGORIES c on c.no_categorie = a.no_categorie " +
            "LEFT JOIN RETRAITS r on a.no_article = r.no_article " +
             "WHERE a.no_article = :noArticle";

     private final String FIND_ALL = "SELECT no_article, nom_article, description, date_debut_encheres, " +
             "date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, etat_vente FROM articles_vendus ";

    private final String FIND_BY_UTILISATEUR = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, " +
            "a.date_fin_encheres, a.prix_initial, a.prix_vente, a.etat_vente," +
            "c.no_categorie, c.libelle, u.no_utilisateur, u.nom, u.prenom, u.pseudo, u.rue, u.code_postal, u.ville, r.rue, r.code_postal, r.ville " +
             "FROM articles_vendus a LEFT JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur " +
            "LEFT JOIN CATEGORIES c on c.no_categorie = a.no_categorie " +
            "LEFT JOIN RETRAITS r on a.no_article = r.no_article " +
             "where a.no_utilisateur = :noUtilisateur";

    private static final String FIND_BY_UTILISATEUR_ET_ETAT_VENTE = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, " +
            "a.date_fin_encheres, a.prix_initial, a.prix_vente, a.etat_vente," +
            "c.no_categorie, c.libelle, u.no_utilisateur, u.nom, u.prenom, u.pseudo, u.rue, u.code_postal, u.ville, r.rue, r.code_postal, r.ville " +
            "FROM articles_vendus a LEFT JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur " +
            "LEFT JOIN CATEGORIES c on c.no_categorie = a.no_categorie " +
            "LEFT JOIN RETRAITS r on a.no_article = r.no_article " +
            "where a.no_utilisateur = :noUtilisateur AND a.etat_vente = :etatVente";

     private final String INSERT = "INSERT INTO articles_vendus(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, etat_vente) " +
             "VALUES (:nomArticle, :description, :dateDebutEncheres, :dateFinEncheres, :prixInitial, :prixVente, :noUtilisateur, :noCategorie, :etatVente)";

     private final String UPDATE = "UPDATE articles_vendus SET nom_article = :nomArticle, description = :description, date_debut_encheres = :dateDebutEncheres, " +
             "date_fin_encheres = :dateFinEncheres, prix_initial = :prixInitial, prix_vente = :prixVente, no_utilisateur = :noUtilisateur, no_categorie = :noCategorie, etat_vente = :etatVente "  +
             "WHERE no_article = :noArticle";

     private final String FIND_NOM = "SELECT nom FROM articles_vendus WHERE no_article = :noArticle";

     private final String FIND_PRIX_VENTE = "SELECT prix_vente FROM articles_vendus WHERE no_article = :noArticle";


     @Autowired
     private NamedParameterJdbcTemplate jdbcTemplate;
     @Autowired
     private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

     public ArticleVenduDAOImpl(NamedParameterJdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
          this.jdbcTemplate = jdbcTemplate;
          this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
     }

     /** Method to get an article
      *
      * @param noArticle
      * @return ArticleVendu
      */

     @Override
     public ArticleVendu getArticleVendu(long noArticle) {
          MapSqlParameterSource params = new MapSqlParameterSource();
          params.addValue("noArticle", noArticle);
          return jdbcTemplate.queryForObject(FIND_BY_NUMBER, params, new ArticleMapper());
     }

     /** Method to create a new article
      *
      * @param articleVendu
      */

     @Override
     public void createArticle (ArticleVendu articleVendu) {
          KeyHolder keyHolder = new GeneratedKeyHolder();

          MapSqlParameterSource namedParameters = new MapSqlParameterSource();
          namedParameters.addValue("nomArticle", articleVendu.getNomArticle());
          namedParameters.addValue("description", articleVendu.getDescription());
          namedParameters.addValue("dateDebutEncheres", articleVendu.getDateDebutEncheres());
          namedParameters.addValue("dateFinEncheres", articleVendu.getDateFinEncheres());
          namedParameters.addValue("prixInitial", articleVendu.getPrixInitial());
          namedParameters.addValue("prixVente", articleVendu.getPrixVente());
          namedParameters.addValue("noUtilisateur", articleVendu.getUtilisateur().getNoUtilisateur());
          namedParameters.addValue("noCategorie", articleVendu.getCategorie().getNoCategorie());
          namedParameters.addValue("etatVente", articleVendu.getEtatVente());

          jdbcTemplate.update(INSERT, namedParameters, keyHolder);

		  if (keyHolder != null && keyHolder.getKey() != null) {
               articleVendu.setNoArticle(keyHolder.getKey().longValue());
          }

     }

     /** Method used to update an article
      *
      * @param articleVendu
      */

     @Override
     public void updateArticle(ArticleVendu articleVendu) {

          MapSqlParameterSource namedParameters = new MapSqlParameterSource();
          namedParameters.addValue("noArticle", articleVendu.getNoArticle());
          namedParameters.addValue("nomArticle", articleVendu.getNomArticle());
          namedParameters.addValue("description", articleVendu.getDescription());
          namedParameters.addValue("dateDebutEncheres", articleVendu.getDateDebutEncheres());
          namedParameters.addValue("dateFinEncheres", articleVendu.getDateFinEncheres());
          namedParameters.addValue("prixInitial", articleVendu.getPrixInitial());
          namedParameters.addValue("prixVente", articleVendu.getPrixVente());
          namedParameters.addValue("noUtilisateur", articleVendu.getUtilisateur().getNoUtilisateur());
          namedParameters.addValue("noCategorie", articleVendu.getCategorie().getNoCategorie());
          namedParameters.addValue("etatVente", articleVendu.getEtatVente());

          jdbcTemplate.update(UPDATE, namedParameters);

     }

     /** Method used to remove an article
      *
      * @param noArticle
      */

     @Override
     public void removeArticle (long noArticle) {
          String sql = "DELETE FROM article_vendu WHERE no_article = noArticle";
          MapSqlParameterSource params = new MapSqlParameterSource();
          params.addValue("noArticle", noArticle);

          namedParameterJdbcTemplate.update(sql, params);
     }

     /** Method used to get a list of all the articles
      *
      * @return a list of articles
      */

     @Override
     public List<ArticleVendu> getAllArticleVendu(){
          return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(ArticleVendu.class));
     }

     /** Method used to get a list of articles sells by someone
      *
      * @return
      */

     @Override
     public List<ArticleVendu> getListArticlesVenduByUtilisateur(Utilisateur utilisateur) {
          Long noUtilisateur = utilisateur.getNoUtilisateur();
          MapSqlParameterSource params = new MapSqlParameterSource();
          params.addValue("noUtilisateur", noUtilisateur);

          return jdbcTemplate.query(FIND_BY_UTILISATEUR, params, new ArticleMapper());
     }

     /** Method used to get the name of an article
      *
      * @param noArticle
      * @return a string with the name of the article
      */

     @Override
     public String findNomArticle (long noArticle){
          MapSqlParameterSource params = new MapSqlParameterSource();
          params.addValue("noArticle", noArticle);

          return jdbcTemplate.queryForObject(FIND_NOM, params, String.class);
     }

     /** Method used to get the actual price of an article
      *
      * @param noArticle
      * @return the number of "prix_vente"
      */

     @Override
     public int findPrixEnchere (long noArticle){
          MapSqlParameterSource params = new MapSqlParameterSource();
          params.addValue("noArticle", noArticle);

          return jdbcTemplate.queryForObject(FIND_PRIX_VENTE, params, Integer.class);
     }

    @Override
    public List<ArticleVendu> getListArticlesVenduByUtilisateurAndEtatvente(Utilisateur utilisateur, Integer etatvente) {
        Long noUtilisateur = utilisateur.getNoUtilisateur();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("noUtilisateur", noUtilisateur);
        params.addValue("etatVente", etatvente);

        return jdbcTemplate.query(FIND_BY_UTILISATEUR_ET_ETAT_VENTE, params, new ArticleMapper());
    }

    class ArticleMapper implements RowMapper<ArticleVendu> {
          @Override
          public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {
               ArticleVendu a = new ArticleVendu();
               a.setNoArticle(rs.getLong("no_article"));
               a.setNomArticle(rs.getString("nom_article"));
               a.setDescription(rs.getString("description"));
               a.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
               a.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
               a.setPrixVente(rs.getInt("prix_vente"));
               a.setPrixInitial(rs.getInt("prix_initial"));
               a.setEtatVente(rs.getInt("etat_vente"));

                  Utilisateur utilisateur = new Utilisateur();
                  utilisateur.setNoUtilisateur(rs.getLong("no_utilisateur"));
                  utilisateur.setNom(rs.getString("NOM"));
                  utilisateur.setPrenom(rs.getString("PRENOM"));
                  utilisateur.setPseudo(rs.getString("PSEUDO"));
                  utilisateur.setRue(rs.getString("rue"));
                  utilisateur.setCodePostal(rs.getString("code_postal"));
                  utilisateur.setVille(rs.getString("ville"));
                  a.setUtilisateur(utilisateur);

                  Categorie categorie = new Categorie();
                  categorie.setNoCategorie(rs.getLong("no_categorie"));
                  categorie.setLibelle(rs.getString("libelle"));
                  a.setCategorie(categorie);

                  Retrait retrait = new Retrait();
                  retrait.setRue(rs.getString("rue"));
                  retrait.setCodePostal(rs.getString("code_postal"));
                  retrait.setVille(rs.getString("ville"));
                  a.setLieuRetrait(retrait);

               return a;
          }

     }


}
