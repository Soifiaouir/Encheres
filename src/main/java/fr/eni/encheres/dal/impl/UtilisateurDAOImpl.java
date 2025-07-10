package fr.eni.encheres.dal.impl;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.dto.UtilisateurDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

    private static final Logger log = LoggerFactory.getLogger(UtilisateurDAOImpl.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

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
        String INSERT_UTILISATEUR = """
                    INSERT  INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
                    VALUES  (:pseudo, :nom, :prenom, :email, :telephone, :rue, :code_postal, :ville, :mot_de_passe, :credit, :administrateur)
                """;
        MapSqlParameterSource map = createParams(utilisateur);

        jdbcTemplate.update(INSERT_UTILISATEUR, map);
    }

    @Override
    @Transactional
    public void updateUtilisateur(Utilisateur utilisateur) {
        String UPDATE_UTILISATEUR = """
                        UPDATE UTILISATEURS
                        SET pseudo = :pseudo, nom = :nom, prenom = :prenom,
                            email = :email, telephone = :telephone, rue = :rue,
                            code_postal = :code_postal, ville = :ville,
                            credit = :credit, administrateur = :administrateur
                        WHERE no_utilisateur = :no_utilisateur
                """;
        MapSqlParameterSource map = createParams(utilisateur);
        map.addValue("no_utilisateur", utilisateur.getNoUtilisateur());  // Ajout du paramètre spécifique à la mise à jour

        log.debug("SQL Query - UPDATE_UTILISATEUR");
        jdbcTemplate.update(UPDATE_UTILISATEUR, map);
    }

    @Override
    public void updatePwd(Utilisateur utilisateur) {
        String UPDATE_PWD = """
                        UPDATE UTILISATEURS 
                        SET mot_de_passe = :mot_de_passe
                        WHERE no_utilisateur = :no_utilisateur
                """;
        MapSqlParameterSource map = createParams(utilisateur);
        map.addValue("mot_de_passe", utilisateur.getMotDePasse());
        map.addValue("no_utilisateur", utilisateur.getNoUtilisateur());

        jdbcTemplate.update(UPDATE_PWD, map);
    }

    @Override
    public Utilisateur readUtilisateurById(long id) {
        String READ_BY_ID = """
                        SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, credit, administrateur
                        FROM UTILISATEURS
                        WHERE no_utilisateur = :no_utilisateur
                """;
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("no_utilisateur", id);

        return jdbcTemplate.queryForObject(READ_BY_ID, map, new BeanPropertyRowMapper<>(Utilisateur.class));
    }

    @Override
    public Utilisateur readUtilisateurByPseudo(String pseudo) {
        String READ_BY_PSEUDO = """
                        SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, credit, administrateur
                        FROM UTILISATEURS
                        WHERE pseudo = :pseudo
                """;
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("pseudo", pseudo);

        return jdbcTemplate.queryForObject(READ_BY_PSEUDO, map, new BeanPropertyRowMapper<>(Utilisateur.class));
    }

    @Override
    @Transactional
    public void deleteUtilisateur(long id) {
        String DELETE_BY_ID = """
                        DELETE FROM UTILISATEURS WHERE  no_utilisateur = :no_utilisateur
                """;
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("no_utilisateur", id);

        jdbcTemplate.update(DELETE_BY_ID, map);
    }

    @Override
    public List<Utilisateur> readAllUtilisateurs() {
        String READ_ALL = """
                        SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, credit, administrateur
                        FROM UTILISATEURS
                """;
        return jdbcTemplate.query(READ_ALL, new BeanPropertyRowMapper<>(Utilisateur.class));
    }

    @Override
    public int checkPseudoExist(String pseudo) {
        String CHECK_PSEUDO = """
                        SELECT COUNT(*) FROM UTILISATEURS
                        WHERE pseudo = :pseudo
                """;

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("pseudo", pseudo);

        Integer count = jdbcTemplate.queryForObject(CHECK_PSEUDO, map, Integer.class);
        return count != null ? count : 0; // Prevenir NullPointerExecption - retourne 0 si jamais count = null
    }

    @Override
    public int checkPseudoExistForOther(String pseudo, long id) {
        String CHECK_PSEUDO_FOR_OTHER = """
                        SELECT COUNT(*) FROM UTILISATEURS
                        WHERE pseudo = :pseudo AND no_utilisateur != :no_utilisateur
                """;

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("pseudo", pseudo);
        map.addValue("no_utilisateur", id);

        Integer count = jdbcTemplate.queryForObject(CHECK_PSEUDO_FOR_OTHER, map, Integer.class);
        return count != null ? count : 0; // Prevenir NullPointerExecption - retourne 0 si jamais count = null
    }

    @Override
    public int checkEmailExist(String email) {
        String CHECK_EMAIL = """
                        SELECT COUNT(*) FROM UTILISATEURS
                        WHERE email = :email
                """;
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("email", email);

        Integer count = jdbcTemplate.queryForObject(CHECK_EMAIL, map, Integer.class);
        return count != null ? count : 0; // Prevenir NullPointerExecption - retourne 0 si jamais count = null
    }

    @Override
    public int checkEmailExistForOther(String email, long id) {
        String CHECK_EMAIL_FOR_OTHER = """
                        SELECT COUNT(*) FROM UTILISATEURS
                        WHERE email = :email AND no_utilisateur != :no_utilisateur
                """;

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("email", email);
        map.addValue("no_utilisateur", id);

        Integer count = jdbcTemplate.queryForObject(CHECK_EMAIL_FOR_OTHER, map, Integer.class);
        return count != null ? count : 0; // Prevenir NullPointerExecption - retourne 0 si jamais count = null
    }

    @Override
    public String checkPwd(long id) {
        String GET_PWD_USER = """
                        SELECT mot_de_passe FROM UTILISATEURS
                        WHERE no_utilisateur = :no_utilisateur
                """;

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("no_utilisateur", id);

        return jdbcTemplate.queryForObject(GET_PWD_USER, map, String.class);
    }

    @Override
    public void addCredit(UtilisateurDTO utilisateurDTO) {
        String ADD_CREDIT = """
                        UPDATE UTILISATEURS
                        SET credit = :credit
                        WHERE no_utilisateur = :no_utilisateur
                """;

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("no_utilisateur", utilisateurDTO.getNoUtilisateur());
        map.addValue("credit", utilisateurDTO.getCredit());

        jdbcTemplate.update(ADD_CREDIT, map);
    }

    @Override
    public void setActive(Utilisateur utilisateur) {
        String SET_ACTIVE = """
                            UPDATE UTILISATEURS
                            SET active = 1
                            WHERE no_utilisateur = :no_utilisateur
                """;

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("no_utilisateur", utilisateur.getNoUtilisateur());
        jdbcTemplate.update(SET_ACTIVE, map);
    }

    @Override
    public void setInactive(Utilisateur utilisateur) {
        String SET_INACTIVE = """
                            UPDATE UTILISATEURS
                            SET active = 0
                            WHERE no_utilisateur = :no_utilisateur
                """;

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("no_utilisateur", utilisateur.getNoUtilisateur());
        jdbcTemplate.update(SET_INACTIVE, map);
    }
}
