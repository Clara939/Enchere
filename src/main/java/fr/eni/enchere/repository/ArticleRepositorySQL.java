package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.repository.rowMapper.ArticleRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepositorySQL implements ArticleRepository{
@Autowired
JdbcTemplate jdbcTemplate;
@Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Article> readAll() {
        return List.of();
    }

    @Override
    public void create(Article article) {
// création d'un keyholder pour generer et gérer l'id
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into Articles (id_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, etat_vente, id_vendeur, id_categorie, id_retrait, id_acheteur)\n " +
                " values (:id_article, :nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :etat_vente, :vendeur, :categorieArticle, :lieuxRetrait, :acheteur)";
        //dans le cadre de l'association on fait la map à la main
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id_article", article.getId_article());
        map.addValue("nom_article", article.getNom_article());
        map.addValue("description", article.getDescription());
        map.addValue("date_debut_encheres", article.getDate_debut_encheres());
        map.addValue("date_fin_encheres", article.getDate_fin_encheres());
        map.addValue("prix_initial", article.getPrix_initial());
        map.addValue("prix_vente", article.getPrix_vente());
        map.addValue("etat_vente", article.getEtat_vente());
        map.addValue("id_vendeur", article.getVendeur().getId_utilisateur());
        map.addValue("id_categorie", article.getCategorieArticle().getId_categorie());
        map.addValue("id_retrait", article.getLieuxRetrait().getId_retrait());

        //si il n'y a pas d'acheteur associé on met null ; l'acheteur est un nombre ici
        if (article.getAcheteur() != null) {
            map.addValue("id_acheteur", article.getAcheteur().getId_utilisateur());
        } else {
            map.addValue("id_acheteur", null);
        }

        //je passe le keyHolder à la requète pour récupérer l'id
        namedParameterJdbcTemplate.update(sql, map, keyHolder);

        //récupération de l'id qui vient d'être créé
        long id = keyHolder.getKey().longValue();

        //associe la clé à l'objet passé en paramètre
        barbie.setId(id);
    }

    @Override
    public Categorie readById(long id) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void update(Article article) {

    }
}
