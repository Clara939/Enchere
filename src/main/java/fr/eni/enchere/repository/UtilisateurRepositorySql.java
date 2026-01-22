package fr.eni.enchere.repository;


import fr.eni.enchere.bo.Utilisateur;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class UtilisateurRepositorySql implements UtilisateurRepository{
    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UtilisateurRepositorySql(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void createUtilisateur(Utilisateur utilisateur) {

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "insert into dbo.Utilisateurs (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur, actif) " +
                "values (:pseudo, :nom, :prenom ,:email ,:telephone ,:rue ,:code_postal ,:ville ,:mot_de_passe ,:credit ,:administrateur ,:actif)";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("pseudo", utilisateur.getPseudo());
        map.addValue("nom", utilisateur.getNom());
        map.addValue("prenom", utilisateur.getPrenom());
        map.addValue("email", utilisateur.getEmail());
        map.addValue("telephone", utilisateur.getTelephone());
        map.addValue("rue", utilisateur.getRue());
        map.addValue("code_postal", utilisateur.getCode_postal());
        map.addValue("ville", utilisateur.getVille());
        map.addValue("mot_de_passe", utilisateur.getMot_de_passe());
        map.addValue("credit", utilisateur.getCredit());
        map.addValue("administrateur", utilisateur.isAdministrateur());
        map.addValue("actif", utilisateur.isActif());

        namedParameterJdbcTemplate.update(sql, map, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        utilisateur.setId_utilisateur(id);
    }

    @Override
    public List<Utilisateur> readAll() {
        String sql = "select * from Utilisateurs";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Utilisateur.class));
    }

    @Override
    public Utilisateur readById(long id) {
        return null;
    }

    @Override
    public void updateUtilisateur(Utilisateur utilisateur) {

    }

    @Override
    public void deleteUtilisateur(long id) {

    }
}
