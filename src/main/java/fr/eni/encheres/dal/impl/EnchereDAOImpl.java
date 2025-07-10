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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EnchereDAOImpl implements EnchereDAO {

    private final String SELECT_ID = "SELECT * FROM ENCHERES WHERE NO_ENCHERE = :noEnchere";

    private final String SELECT_BY_USER =  "SELECT E.NO_ENCHERE, E.DATE_ENCHERE, E.MONTANT_ENCHERE, E.NO_ARTICLE, E.NO_UTILISATEUR, " +
            "U.NOM, U.PRENOM, U.PSEUDO, A.NOM_ARTICLE FROM ENCHERES E " +
            "LEFT JOIN UTILISATEURS U ON U.NO_UTILISATEUR = E.NO_UTILISATEUR " +
            "LEFT JOIN ARTICLES_VENDUS A ON A.NO_ARTICLE = E.NO_ARTICLE " +
            "WHERE E.NO_UTILISATEUR = :noUtilisateur";;

    private final String SELECT_ARTICLE = "SELECT E.NO_ENCHERE, E.DATE_ENCHERE, E.MONTANT_ENCHERE, E.NO_UTILISATEUR, E.NO_ARTICLE, " +
            "U.NOM, U.PRENOM, U.PSEUDO, A.NOM_ARTICLE FROM ENCHERES E " +
            "LEFT JOIN UTILISATEURS U ON U.NO_UTILISATEUR = E.NO_UTILISATEUR " +
            "LEFT JOIN ARTICLES_VENDUS A ON A.NO_ARTICLE = E.NO_ARTICLE " +
            "WHERE E.NO_ARTICLE = :noArticle";


    private final String SELECT_ALL = "SELECT E.NO_ENCHERE, E.DATE_ENCHERE, E.MONTANT_ENCHERE, " +
            "E.NO_ARTICLE, E.NO_UTILISATEUR," + " U.NOM, U.PRENOM, U.PSEUDO, A.NOM_ARTICLE FROM ENCHERES E " +
            "LEFT JOIN UTILISATEURS U ON U.NO_UTILISATEUR = E.NO_UTILISATEUR " +
            "LEFT OUTER JOIN DBO.ARTICLES_VENDUS A ON A.NO_ARTICLE = E.NO_ARTICLE;";

    private final String CREATE_ENCHERE ="INSERT INTO ENCHERES (DATE_ENCHERE, MONTANT_ENCHERE ,NO_ARTICLE,NO_UTILISATEUR)" +
            " VALUES (:dateEnchere,:montantEnchere, :noArticle, :noUtilisateur)";

    private final String DELETE_ENCHERE ="DELETE FROM ENCHERES WHERE NO_ENCHERE = :noEnchere";

    private final String UPDATE_ENCHERE = "UPDATE ENCHERES SET DATE_ENCHERE = :dateEnchere, " +
            "MONTANT_ENCHERE = :montantEnchere,"+" NO_ARTICLE = :noArticle," +
            "NO_UTILISATEUR = :noUtilisateur where NO_ENCHERE = :noEnchere";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public EnchereDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Enchere> readListEnchere() {
        return jdbcTemplate.query(SELECT_ALL, new EnchereMapper());
    }

    @Override
    public Enchere readByNoEnchere(long noEnchere) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("noEnchere", noEnchere);

        return jdbcTemplate.queryForObject(SELECT_ID,params, new BeanPropertyRowMapper<>(Enchere.class));
    }

    @Override
    public void createEnchere(Enchere enchere, Long noArticle) {
        KeyHolder key = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("dateEnchere", enchere.getDateEnchere());
        params.addValue("montantEnchere", enchere.getMontantEnchere());
        params.addValue("noArticle", enchere.getArticleConcerne().getNoArticle());
        params.addValue("noUtilisateur", enchere.getEncherisseur().getNoUtilisateur());

        jdbcTemplate.update(CREATE_ENCHERE, params, key);

        if ( key != null && key.getKey() != null ) {
            enchere.setNoEnchere(key.getKey().longValue());
        }

    }

    @Override
    public void deleteEnchere(long noEnchere) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("noEnchere", noEnchere);

        jdbcTemplate.update(DELETE_ENCHERE, params);
    }

    @Override
    public void updateEnchere(Enchere enchere) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("dateEnchere", enchere.getDateEnchere());
        params.addValue("montantEnchere", enchere.getMontantEnchere());
        params.addValue("noArticle", enchere.getArticleConcerne().getNoArticle());
        params.addValue("noUtilisateur", enchere.getEncherisseur().getNoUtilisateur());

        jdbcTemplate.update(UPDATE_ENCHERE, params);

    }

    @Override
    public List<Enchere> readByArticle(Long noArticle) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("noArticle",noArticle);
        return jdbcTemplate.query(SELECT_ARTICLE, params, new EnchereMapper());
    }

    @Override
    public List<Enchere> readByUser(Long noUtilisateur) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("noUtilisateur",noUtilisateur);
        return jdbcTemplate.query(SELECT_BY_USER, params, new EnchereMapper());
    }

    class EnchereMapper implements RowMapper<Enchere> {
        @Override
        public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
            Enchere e = new Enchere();
            e.setNoEnchere(rs.getLong("NO_ENCHERE"));
            e.setDateEnchere(rs.getDate("DATE_ENCHERE").toLocalDate());
            e.setMontantEnchere(rs.getInt("MONTANT_ENCHERE"));

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNoUtilisateur(rs.getLong("NO_UTILISATEUR"));
            utilisateur.setNom(rs.getString("NOM"));
            utilisateur.setPrenom(rs.getString("PRENOM"));
            utilisateur.setPseudo(rs.getString("PSEUDO"));

            e.setEncherisseur(utilisateur);

            ArticleVendu articleVendu = new ArticleVendu();
            articleVendu.setNoArticle(rs.getLong("NO_ARTICLE"));
            articleVendu.setNomArticle(rs.getString("NOM_ARTICLE"));

            e.setArticleConcerne(articleVendu);

            return e;
        }
    }
}
