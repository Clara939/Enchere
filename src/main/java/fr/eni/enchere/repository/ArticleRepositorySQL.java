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

//    @Override
//    public List<Article> readAll() {
//        String sql = "select articles.id_article as id_article, articles.nom_article as nom_article, articles.description as description, articles.date_debut_encheres as date_debut_encheres, articles.date_fin_encheres as date_fin_encheres, articles.prix_initial as prix_initial, articles.prix_vente as prix_vente, articles.etat_vente as etat_vente, id_vendeur, utilisateurs.nom as nom_vendeur, utilisateurs.prenom as prenom_vendeur, articles.id_categorie, categories.libelle as libelle_categorie, articles.id_retrait, retraits.rue as rue_retrait, retraits.code_postal as code_postal_retrait, retraits.ville as ville_retrait, articles.id_acheteur, utilisateurs.nom as nom_acheteur, utilisateurs.prenom as prenom_acheteur from Articles\n " +
//                "    left join utilisateurs on utilisateurs.id_utilisateur = articles.id_vendeur\n " +
//                " left join categories on categories.id_categorie = articles.id_categorie\n " +
//                "left join retraits on retraits.id_retrait = articles.id_retrait\n " +
//                "left join utilisateurs on utilisateurs.id_utilisateur = articles.id_acheteur";
//
//        //ici, on crée un ArticleRowMapper pour que la requête récupère et transforme correctement les articles à partir du resultset de la requête
//        //lancement de la requète récupération de la liste d'articles qui est passée en retour
//        return jdbcTemplate.query(sql, new ArticleRowMapper());
//        ;
//    }

    @Override
    public List<Article> readAll() {
        return List.of();
    }

    @Override
    public void create(Article article) {
// création d'un keyholder pour generer et gérer l'id
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into Articles (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, etat_vente, id_vendeur, id_categorie, id_retrait, id_acheteur)\n " +
                " values (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :etat_vente, :id_vendeur, :id_categorie, :id_retrait, :id_acheteur)";
        //dans le cadre de l'association on fait la map à la main
        MapSqlParameterSource map = new MapSqlParameterSource();
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
        article.setId_article(id);
    }

    @Override
    public Article readById(long id) {
        return null;
    }

    @Override
    public void delete(long id) {
        String sql = "delete from articles where id_article=:id_article";
        //impossible d'utiliser BeanPropertySqlParameterSource il n'y a pas d'objet
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id_article", id);

        //lancement de la requête
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void update(Article article) {
        String sql = "update dbo.Articles set nom_article=:nom_article, description=:description, date_debut_encheres=:date_debut_encheres, date_fin_encheres=:date_fin_encheres, prix_initial=:prix_initial, prix_vente=:prix_vente, etat_vente=:etat_vente, id_vendeur=:id_vendeur, id_categorie=:id_categorie, id_retrait=:id_retrait, id_acheteur=:id_acheteur where id_article=:id_article";
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

        //lancement de la requête
        namedParameterJdbcTemplate.update(sql, map);
    }
}
