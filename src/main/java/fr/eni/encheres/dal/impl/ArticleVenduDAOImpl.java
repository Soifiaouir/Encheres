package fr.eni.encheres.dal.impl;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
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

     private final String FIND_BY_NUMBER = "SELECT no_article, nom_article, description, date_debut_encheres, " +
             "date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie FROM articles_vendus " +
             "WHERE no_article = :noArticle";

     private final String FIND_ALL = "SELECT no_article, nom_article, description, date_debut_encheres, " +
             "date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie FROM articles_vendus ";

//     private final String FIND_ALL = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, " +
//             "a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, " +
//             "u.nom, u.prenom, u.pseudo, " +
//             "e.no_enchere, e.date_enchere, e.montant_enchere, e.no_utilisateur, " +
//             "eu.nom, eu.prenom, eu.pseudo " +
//             "FROM articles_vendus a " +
//             "LEFT JOIN utilisateurs u ON u.no_utilisateur = a.no_utilisateur " + // créateur/propriétaire de l'article
//             "LEFT JOIN encheres e ON e.no_article = a.no_article " +
//             "LEFT JOIN utilisateurs eu ON eu.no_utilisateur = e.no_utilisateur";

     private final String INSERT = "INSERT INTO articles_vendus(no_article, nom_article, description, date_debut_encheres, \" +\n" +
             "             \"date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)"
             + " VALUES (:noArticle, :nomArticle, :description, :dateDebutEncheres, :dateFinEncheres, :prixInitial, :prixVente, :noUtilisateur, :noCategorie)";

     private final String UPDATE = "UPDATE INTO articles_vendus(no_article, nom_article, description, date_debut_encheres, \" +\n" +
             "             \"date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)"
             + " VALUES (:noArticle, :nomArticle, :description, :dateDebutEncheres, :dateFinEncheres, :prixInitial, :prixVente, :noUtilisateur, :noCategorie)";


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
          params.addValue("no_article", noArticle);
          return jdbcTemplate.queryForObject(FIND_BY_NUMBER, params,
                  new BeanPropertyRowMapper<>(ArticleVendu.class));
     }

     /** Method to create a new article
      *
      * @param articleVendu
      */

     @Override
     public void createArticle (ArticleVendu articleVendu) {
          KeyHolder keyHolder = new GeneratedKeyHolder();

          MapSqlParameterSource namedParameters = new MapSqlParameterSource();
          namedParameters.addValue("nom_article", articleVendu.getNomArticle());
          namedParameters.addValue("description", articleVendu.getDescription());
          namedParameters.addValue("date_debut_encheres", articleVendu.getDateDebutEncheres());
          namedParameters.addValue("date_fin_encheres", articleVendu.getDateFinEncheres());
          namedParameters.addValue("prix_initial", articleVendu.getPrixInitial());
          namedParameters.addValue("prix_vente", articleVendu.getPrixVente());
          namedParameters.addValue("no_utilisateur", articleVendu.getUtilisateur().getNoUtilisateur());
          namedParameters.addValue("no_categorie", articleVendu.getCategorie().getNoCategorie());

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
          namedParameters.addValue("nom_article", articleVendu.getNomArticle());
          namedParameters.addValue("description", articleVendu.getDescription());
          namedParameters.addValue("date_debut_encheres", articleVendu.getDateDebutEncheres());
          namedParameters.addValue("date_fin_encheres", articleVendu.getDateFinEncheres());
          namedParameters.addValue("prix_initial", articleVendu.getPrixInitial());
          namedParameters.addValue("prix_vente", articleVendu.getPrixVente());
          namedParameters.addValue("no_utilisateur", articleVendu.getUtilisateur().getNoUtilisateur());
          namedParameters.addValue("no_categorie", articleVendu.getCategorie().getNoCategorie());

          jdbcTemplate.update(UPDATE, namedParameters);

     }

     /** Method used to remove an article
      *
      * @param noArticle
      */

     @Override
     public void removeArticle (long noArticle) {
          String sql = "DELETE FROM article_vendu WHERE id = id";
          MapSqlParameterSource params = new MapSqlParameterSource();
          params.addValue("no_article", noArticle);

          namedParameterJdbcTemplate.update(sql, params);
     }

     /** Method used to get a list of all the articles
      *
      * @return a list of articles
      */

     @Override
     public List<ArticleVendu> getAllArticleVendu(){
          return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(ArticleVendu.class));
//          return jdbcTemplate.query(FIND_ALL, new ArticleMapper());

     }

     /** Method used to get the name of an article
      *
      * @param noArticle
      * @return a string with the name of the article
      */

     @Override
     public String findNomArticle (long noArticle){
          MapSqlParameterSource params = new MapSqlParameterSource();
          params.addValue("no_article", noArticle);

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
          params.addValue("no_article", noArticle);

          return jdbcTemplate.queryForObject(FIND_PRIX_VENTE, params, Integer.class);
     }

//     class ArticleMapper implements RowMapper<ArticleVendu> {
//          @Override
//          public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {
//               ArticleVendu a = new ArticleVendu();
//               a.setNoArticle(rs.getLong("no_article"));
//               a.setNomArticle(rs.getString("nom_article"));
//               a.setDescription(rs.getString("description"));
//               a.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
//               a.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
//               a.setPrixVente(rs.getInt("prix_vente"));
//
//               Utilisateur utilisateur = new Utilisateur();
//               utilisateur.setNoUtilisateur(rs.getLong("NO_UTILISATEUR"));
//               utilisateur.setNom(rs.getString("NOM"));
//               utilisateur.setPrenom(rs.getString("PRENOM"));
//               utilisateur.setPseudo(rs.getString("PSEUDO"));
//               a.setUtilisateur(utilisateur);
//
////               Enchere enchere = new  Enchere();
////               enchere.setNoEnchere(rs.getLong("NO_ENCHERE"));
////               enchere.setDateEnchere(rs.getDate("date_encheres").toLocalDate());
////               enchere.setMontantEnchere(rs.getInt("montant_encheres"));
////               a.setLstEncheres(enchere);
//               return a;
//          }
//     }

}
