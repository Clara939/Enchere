package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.repository.rowMapper.EnchereRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnchereRepositorySql implements EnchereRepository{

        JdbcTemplate jdbcTemplate;
        NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public EnchereRepositorySql(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void createEnchere(Enchere enchere) {

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "insert into Encheres (date_enchere, montant_enchere, id_encherisseur, id_article)\n " +
                    "values (:date_enchere, :montant_enchere, :id_encherisseur, :id_article)";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("date_enchere", enchere.getDate_enchere());
        map.addValue("montant_enchere", enchere.getMontant_enchere());
        map.addValue("id_encherisseur", enchere.getEncherisseur().getId_utilisateur());
        map.addValue("id_article", enchere.getArticles().getId_article());

        namedParameterJdbcTemplate.update(sql, map, keyHolder);

//        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        long id = keyHolder.getKey().longValue();

        enchere.setId_enchere(id);
    }

    @Override
    public List<Enchere> readAll() {
        String sql = "select Encheres.id_enchere as id_enchere, encheres.id_encherisseur as id_encherisseur, utilisateurs.nom as nom_encherisseur, utilisateurs.prenom as prenom_encherisseur, utilisateurs.email as email_encherisseur, encheres.id_article as id_article, encheres.date_enchere as date_enchere, encheres.montant_enchere as montant_enchere, articles.nom_article as nom_article, articles.description as description, articles.prix_vente as prix_vente, articles.etat_vente as etat_vente, articles.date_debut_encheres as date_debut_encheres, articles.date_fin_encheres as date_fin_encheres from Encheres\n " +
                " left join Utilisateurs on encheres.id_encherisseur = utilisateurs.id_utilisateur\n " +
                " left join Articles on encheres.id_article = articles.id_article";

        return jdbcTemplate.query(sql, new EnchereRowMapper());
    }

    @Override
    public Enchere readById(long id) {
        String sql = "select Encheres.id_enchere as id_enchere, encheres.id_encherisseur as id_encherisseur, utilisateurs.nom as nom_encherisseur, utilisateurs.prenom as prenom_encherisseur, utilisateurs.email as email_encherisseur, encheres.id_article as id_article, encheres.date_enchere as date_enchere, encheres.montant_enchere as montant_enchere, articles.nom_article as nom_article, articles.description as description, articles.prix_vente as prix_vente, articles.etat_vente as etat_vente, articles.date_debut_encheres as date_debut_encheres, articles.date_fin_encheres as date_fin_encheres from Encheres\n " +
                " left join Utilisateurs on encheres.id_encherisseur = utilisateurs.id_utilisateur\n " +
                " left join Articles on encheres.id_article = articles.id_article\n " +
                "WHERE id_enchere = :id";

        return namedParameterJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id), new EnchereRowMapper());
    }

    @Override
    public void updateEnchere(Enchere enchere) {
        String sql = "update Encheres set date_enchere=:date_enchere, montant_enchere=:montant_enchere, id_encherisseur=:id_encherisseur, id_article=:id_article " +
                "where id_enchere= :id_enchere";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id_enchere", enchere.getId_enchere());
        map.addValue("date_enchere", enchere.getDate_enchere());
        map.addValue("montant_enchere", enchere.getMontant_enchere());
        map.addValue("id_encherisseur", enchere.getEncherisseur().getId_utilisateur());
        map.addValue("id_article", enchere.getArticles().getId_article());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteEnchere(long id) {
        String sql = "delete from Encheres where id_enchere= :id";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", id);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public List<Enchere> readAllForOneArticle(long id) {
        String sql = "select Encheres.id_enchere as id_enchere, encheres.id_encherisseur as id_encherisseur, utilisateurs.nom as nom_encherisseur, utilisateurs.prenom as prenom_encherisseur, utilisateurs.email as email_encherisseur, encheres.id_article as id_article, encheres.date_enchere as date_enchere, encheres.montant_enchere as montant_enchere, articles.nom_article as nom_article, articles.description as description, articles.prix_vente as prix_vente, articles.etat_vente as etat_vente, articles.date_debut_encheres as date_debut_encheres, articles.date_fin_encheres as date_fin_encheres from Encheres\n " +
                " left join Utilisateurs on encheres.id_encherisseur = utilisateurs.id_utilisateur\n " +
                " left join Articles on encheres.id_article = articles.id_article\n " +
                "WHERE id_article = :id";

        return jdbcTemplate.query(sql, new EnchereRowMapper());
    }
}
