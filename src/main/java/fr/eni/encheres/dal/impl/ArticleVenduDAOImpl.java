package fr.eni.encheres.dal.impl;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.ArticleVenduDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ArticleVenduDAOImpl implements ArticleVenduDAO {

     private final String FIND_BY_NUMBER = "SELECT no_article, nom_article, description, date_debut_encheres, " +
             "date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie FROM articles_vendus " +
             "WHERE no_article = :noArticle";

     private final String FIND_ALL = "SELECT no_article, nom_article, description, date_debut_encheres, " +
             "date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie FROM articles_vendus ";

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



}
