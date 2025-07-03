package fr.eni.encheres.dal.impl;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.RetraitDAO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class RetraitDAOImpl implements RetraitDAO {
    private final String CREATE_RETRAIT = "INSERT INTO RETRAITS (NO_ARTICLE, RUE, CODE_POSTALE, VILLE) VALUES (:no_article, :rue, :code_postale, :ville";
    private final String SELECT_BY_ARTICLE = "SELECT NO_ARTICLE, RUE, CODE_POSTALE, VILLE FROM RETRAITS WHERE NO_ARTICLE = :no_article";
    private final String UPDATE_RETRAIT ="UPDATE RETRAITS SET RUE =:rue, CODE_POSTALE = :codePostale," +
            "VILLE = :ville WHERE NO_ARTICLE = :no_article ";
    private final String DELETE_RETRAIT ="DELETE FROM RETRAITS WHERE NO_ARTICLE = :no_article ";



    private NamedParameterJdbcTemplate jdbcTemplate;

    public RetraitDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Retrait readByArticle(long noArticle) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_article", noArticle);
        return jdbcTemplate.queryForObject(SELECT_BY_ARTICLE, params, new RetraitMapper());
    }

    @Override
    public void createRetrait(Retrait retrait) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rue", retrait.getRue());
        params.addValue("code_postale", retrait.getCodePostal());
        params.addValue("ville", retrait.getVille());
        params.addValue("no_article", retrait.getArticleVendu().getNoArticle());

        jdbcTemplate.update(CREATE_RETRAIT, params);
    }

    @Override
    public void updateRetrait(Retrait retrait) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rue", retrait.getRue());
        params.addValue("code_postale", retrait.getCodePostal());
        params.addValue("ville", retrait.getVille());
        params.addValue("no_article", retrait.getArticleVendu().getNoArticle());

        jdbcTemplate.update(UPDATE_RETRAIT, params);

    }

    @Override
    public void deleteRetrait(long noArticle) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_article", noArticle);
        jdbcTemplate.update(DELETE_RETRAIT, params);

    }


    class RetraitMapper implements RowMapper<Retrait> {
        @Override
        public Retrait mapRow(ResultSet rs, int rowNum) throws SQLException {
            Retrait r = new Retrait();

            //Association pour les articles vendus
            ArticleVendu article  = new ArticleVendu();
            article.setNoArticle(rs.getLong("NO_ARTICLE"));
            r.setArticleVendu(article);

            r.setRue(rs.getString("RUE"));
            r.setCodePostal(rs.getString("CODE_POSTALE"));
            r.setVille(rs.getString("VILLE"));

            return r;
        }
    }

}


