package fr.eni.encheres.dal.impl;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.EnchereDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EnchereDAOImpl implements EnchereDAO {

    private final String SELECT_ID = "SELECT * FROM ENCHERES WHERE NO_ENCHERE = :noEnchere";
    private final String SELECT_BY_USER = "SELECT * FROM ENCHERES WHERE NO_UTILISATEUR = :noUtilisateur";
    private final String SELECT_ARTICLE = "SELECT * FROM ENCHERES WHERE NO_ARTICLE = :noArticle";
    private final String CREATE_ENCHERE ="INSERT INTO ENCHERES (DATE_ENCHERE, MONTANT_ENCHERE,NO_ARTICLE,NO_UTILISATEUR)" +
            " VALUE (date_enchere,:montant_enchere, :no_article, :no_utilisateur)";
    private final String FIND_ALL_ENCHERES ="SELECT NO_ENCHERE, DATE_ENCHERE, MONTANT_ENCHERE, NO_ARTICLE, NO_UTILISATEUR FROM ENCHERES";

    private final String DELETE_ENCHERE ="DELETE FROM ENCHERES WHERE NO_ENCHERE = :noEnchere";
    private final String UPDATE_ENCHERE = "UPDATE ENCHERES SET DATE_ENCHERE = :dateeEnchere, " +
            "MONTANT-ENCHERE = :montant_enchere, NO_ENCHERE= :no_enchere," +
            " NO_UTILISATEUR = :no_utilisateur where NO_ENCHERE = :no_enchere";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public EnchereDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Enchere readByNoEnchere(long noEnchere) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_enchere", noEnchere);

        return jdbcTemplate.queryForObject(SELECT_ID,params, new BeanPropertyRowMapper<>(Enchere.class));
    }

    @Override
    public List<Enchere> findListEncheres(long noArticleVendu) {

        return null;
    }

    @Override
    public void createEnchere(Enchere enchere) {
        KeyHolder key = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_enchere", enchere.getNoEnchere());
        params.addValue("date_enchere", enchere.getDateEnchere());
        params.addValue("montant_enchere", enchere.getMontantEnchere());
        params.addValue("no_article", enchere.getArticleConcerne().getNoArticle());
        params.addValue("no_utilisateur", enchere.getEncherisseur().getNoUtilisateur());

        jdbcTemplate.update(CREATE_ENCHERE, params, key);

        if ( key != null && key.getKey() != null ) {
            enchere.setNoEnchere(key.getKey().longValue());
        }

    }

    @Override
    public void deleteEnchere(long noEnchere) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_enchere", noEnchere);

        jdbcTemplate.update(DELETE_ENCHERE, params);
    }

    @Override
    public void updateEnchere(Enchere enchere) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("date_enchere", enchere.getDateEnchere());
        params.addValue("montant_enchere", enchere.getMontantEnchere());
        params.addValue("montant_enchere", enchere.getMontantEnchere());
        params.addValue("no_article", enchere.getArticleConcerne().getNoArticle());
        params.addValue("no_utilisateur", enchere.getEncherisseur().getNoUtilisateur());

        jdbcTemplate.update(UPDATE_ENCHERE, params);

    }

    @Override
    public List<Enchere> readByArticle(Long noArticle) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_article",noArticle);
        return jdbcTemplate.query(SELECT_ARTICLE, params, new EnchereMapper());
    }

    @Override
    public List<Enchere> readByUser(Long noUtilisateur) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_utilisateur",noUtilisateur);
        return jdbcTemplate.query(SELECT_BY_USER, params, new EnchereMapper());
    }

    class EnchereMapper implements RowMapper<Enchere> {
        @Override
        public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
            Enchere e = new Enchere();
            e.setNoEnchere(rs.getLong("NO_ENCHERE"));
            e.setDateEnchere(rs.getDate("DATE_ENCHERE").toLocalDate());
            e.setMontantEnchere(rs.getInt("MONTANT-ENCHERE"));

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNoUtilisateur(rs.getLong("NO_UTILISATEUR"));
            e.setEncherisseur(utilisateur);

            ArticleVendu articleVendu = new ArticleVendu();
            articleVendu.setNoArticle(rs.getLong("NO_ARTICLE"));
            e.setArticleConcerne(articleVendu);

            return e;
        }
    }
}
