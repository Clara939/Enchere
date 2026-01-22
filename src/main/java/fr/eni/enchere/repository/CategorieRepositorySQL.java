package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategorieRepositorySQL implements CategorieRepository {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Categorie> readAll() {
        String sql = "SELECT * FROM Categories";
            //on ajoute le BeanPropertyRowMapper pour gérer la liste des objets
            //lancement de la requête récupération de la liste de catégories qui est passé en retour
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Categorie.class));
    }

    @Override
    public void create(Categorie categorie) {
        //création de l'élément qui permet de récupérer l'id
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into categories(libelle) values (:libelle)";

        // à utiliser lorsque les noms de la table et de la bo sont identiques
        BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(categorie);

        //je passe le keyHolder à la requète pour récupérer l'id
        namedParameterJdbcTemplate.update(sql, map, keyHolder);

        //récupération de l'id qui vient d'être créé
        long id = keyHolder.getKey().longValue();

        //associe la clé à l'objet passé en paramètre
        categorie.setId_categorie(id);
    }

    @Override
    public Categorie readById(long id) {
        String sql = "SELECT * FROM Categories WHERE id_categorie=:id_categorie";
//on crée l'association entre le :id et l'id passé en paramètre
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id_categorie", id);
        //lancement de la requète récupération de l'objet type qui est passé en retour
        return namedParameterJdbcTemplate.queryForObject(sql, map,
                new BeanPropertyRowMapper<>(Categorie.class));
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM Categories WHERE id_categorie=:id_categorie";
//on crée l'association entre le :id et l'id passé en paramètre
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id_categorie", id);
        //lancement de la requète récupération de l'objet type qui est passé en retour
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void update(Categorie categorie) {
        String sql = "UPDATE Categories SET libelle=:libelle where id_categorie=:id_categorie";
        //utilisation de l'objet type pour gérer l'association entre les paramètres de la
        // requête et les valeurs à utiliser
        BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(categorie);
        //lancement de la requète
        namedParameterJdbcTemplate.update(sql, map);
    }
}
