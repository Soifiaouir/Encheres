package fr.eni.encheres.dal.impl;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategorieDAOImpl implements CategorieDAO {
    private final String SELECT_ALL = "SELECT  NO_CATEGORIE, LIBELLE FROM CATEGORIES";
    private final String SELECT_ID = "SELECT NO_CATEGORIE, LIBELLE FROM CATEGORIES WHERE NO_CATEGORIE = :noCategorie";
    private final String CREATE_CATEGORIE = "INSERT INTO CATEGORIES (LIBELLE) VALUES (:libelle)";
    private final String DELETE_CATEGORIE = "DELETE FROM CATEGORIES WHERE NO_CATEGORIE = :noCategorie";
    private final String UPDATE_CATEGORIE = "UPDATE CATEGORIES SET LIBELLE = :libelle WHERE NO_CATEGORIE = :noCategorie";

    private NamedParameterJdbcTemplate jdbcTemplate;

    public CategorieDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Categorie readNoCategorie(long noCategorie) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("noCategorie", noCategorie);

        return jdbcTemplate.queryForObject(SELECT_ID, params, new BeanPropertyRowMapper<>(Categorie.class));
    }


    @Override
    public List<Categorie> getAll() {

        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Categorie.class));
    }

    @Override
    public void createCategorie(Categorie categorie) {
        KeyHolder key = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("libelle", categorie.getLibelle());

        jdbcTemplate.update(CREATE_CATEGORIE, params, key);

        if ( key != null && key.getKey() != null ) {
            categorie.setNoCategorie(key.getKey().longValue());
        }

    }

    @Override
    public void updateCategorie(Categorie categorie) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_categorie",categorie.getNoCategorie());
        params.addValue("libelle", categorie.getLibelle());

        jdbcTemplate.update(UPDATE_CATEGORIE, params);
    }

    @Override
    public void deleteCategorie(long noCategorie) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("noCategorie", noCategorie);
        jdbcTemplate.update(DELETE_CATEGORIE, params);
    }
}
