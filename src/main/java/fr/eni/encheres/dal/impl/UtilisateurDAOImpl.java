package fr.eni.encheres.dal.impl;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT_UTILISATEUR = """
            INSERT  INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
            VALUES  (:pseudo, :nom, :prenom, :email, :telephone, :rue, :code_postal, :ville, :mot_de_passe, :credit, :administrateur)
    """;
    private static final String UPDATE_UTILISATEUR = """
            UPDATE UTILISATEURS
            SET pseudo = :pseudo, nom = :nom, prenom = :prenom,
                email = :email, telephone = :telephone, rue = :rue,
                code_postal = :code_postal, ville = :ville, mot_de_passe = :mot_de_passe,
                credit = :credit, administrateur = :administrateur
            WHERE no_utilisateur = :no_utilisateur
    """;
    private static final String READ_BY_ID = """
            SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, credit, administrateur
            FROM UTILISATEURS
            WHERE no_utilisateur = :no_utilisateur
    """;
    private static final String READ_BY_PSEUDO = """
            SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, credit, administrateur
            FROM UTILISATEURS
            WHERE pseudo = :pseudo
    """;
    private static final String READ_ALL = """
            SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, credit, administrateur
            FROM UTILISATEURS
    """;
    private static final String DELETE_BY_ID = """
            DELETE FROM UTILISATEURS WHERE  no_utilisateur = :no_utilisateur
    """;
    private static final String CHECK_PSEUDO= """
            SELECT COUNT(*) FROM UTILISATEURS
            WHERE pseudo = :pseudo
    """;private static final String CHECK_MAIL= """
            SELECT COUNT(*) FROM UTILISATEURS
            WHERE email = :email
    """;

    public UtilisateurDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Refractor mapping pour eviter la duplication de code pour INSERT/UPDATE
    private MapSqlParameterSource createParams(Utilisateur utilisateur) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("pseudo", utilisateur.getPseudo());
        map.addValue("nom", utilisateur.getNom());
        map.addValue("prenom", utilisateur.getPrenom());
        map.addValue("email", utilisateur.getEmail());
        map.addValue("telephone", utilisateur.getTelephone());
        map.addValue("rue", utilisateur.getRue());
        map.addValue("code_postal", utilisateur.getCodePostal());
        map.addValue("ville", utilisateur.getVille());
        map.addValue("mot_de_passe", utilisateur.getMotDePasse());
        map.addValue("credit", utilisateur.getCredit());
        map.addValue("administrateur", utilisateur.isAdministrateur());
        return map;
    }

    @Override
    @Transactional
    public void createUtilisateur(Utilisateur utilisateur) {
        MapSqlParameterSource map = createParams(utilisateur);

        jdbcTemplate.update(INSERT_UTILISATEUR, map);
    }

    @Override
    @Transactional
    public void updateUtilisateur(Utilisateur utilisateur) {
        MapSqlParameterSource map = createParams(utilisateur);
        map.addValue("no_utilisateur", utilisateur.getNoUtilisateur());  // Ajout du paramètre spécifique à la mise à jour

        jdbcTemplate.update(UPDATE_UTILISATEUR, map);
    }

    @Override
    public Utilisateur readUtilisateurById(long id) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("no_utilisateur", id);

        return jdbcTemplate.queryForObject(READ_BY_ID, map, new BeanPropertyRowMapper<>(Utilisateur.class));
    }

    @Override
    public Utilisateur readUtilisateurByPseudo(String pseudo) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("pseudo", pseudo);

        return jdbcTemplate.queryForObject(READ_BY_PSEUDO, map, new BeanPropertyRowMapper<>(Utilisateur.class));
    }

    @Override
    @Transactional
    public void deleteUtilisateur(long id) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("no_utilisateur", id);

        jdbcTemplate.update(DELETE_BY_ID, map);
    }

    @Override
    public List<Utilisateur>  readAllUtilisateurs() {
        return jdbcTemplate.query(READ_ALL, new BeanPropertyRowMapper<>(Utilisateur.class));
    }

    @Override
    public int checkUtilisateurByPseudo(String pseudo) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("pseudo", pseudo);

        Integer count = jdbcTemplate.queryForObject(CHECK_PSEUDO, map, Integer.class);
        return count != null ? count : 0; // Prevenir NullPointerExecption - retourne 0 si jamais count = null
    }

    @Override
    public int checkUtilisateurByEmail(String email) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("email", email);

        Integer count = jdbcTemplate.queryForObject(CHECK_MAIL, map, Integer.class);
        return count != null ? count : 0; // Prevenir NullPointerExecption - retourne 0 si jamais count = null
    }
}
