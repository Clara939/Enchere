package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Article;
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
        String sql = "select articles.id_article as id_article, articles.nom_article as nom_article, articles.description as description, articles.date_debut_encheres as date_debut_encheres, articles.date_fin_encheres as date_fin_encheres, articles.prix_initial as prix_initial, articles.prix_vente as prix_vente, articles.etat_vente as etat_vente, articles.photo_article as photo_article, id_vendeur, vendeurs.pseudo as pseudo_vendeur, vendeurs.nom as nom_vendeur, vendeurs.prenom as prenom_vendeur, articles.id_categorie, categories.libelle as libelle_categorie, articles.id_retrait, retraits.rue as rue_retrait, retraits.code_postal as code_postal_retrait, retraits.ville as ville_retrait, articles.id_acheteur, acheteurs.nom as nom_acheteur, acheteurs.prenom as prenom_acheteur from Articles\n " +
                "    left join utilisateurs as vendeurs on vendeurs.id_utilisateur = articles.id_vendeur\n " +
                " left join categories on categories.id_categorie = articles.id_categorie\n " +
                "left join retraits on retraits.id_retrait = articles.id_retrait\n " +
                "left join utilisateurs as acheteurs on acheteurs.id_utilisateur = articles.id_acheteur";

        //ici, on crée un ArticleRowMapper pour que la requête récupère et transforme correctement les articles à partir du resultset de la requête
        //lancement de la requète récupération de la liste d'articles qui est passée en retour
        return jdbcTemplate.query(sql, new ArticleRowMapper());

    }

    @Override
    public void create(Article article) {
// création d'un keyholder pour generer et gérer l'id
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into Articles (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, etat_vente, photo_article, id_vendeur, id_categorie, id_retrait, id_acheteur) " +
                "values (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :etat_vente, :photo_article, :id_vendeur, :id_categorie, :id_retrait, :id_acheteur)";
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
        map.addValue("id_retrait", article.getLieuxRetrait() != null ? article.getLieuxRetrait().getId_retrait() : null);
        map.addValue("photo_article", "/image/encheres_marteau.jpg");

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
    public Article readById(long id_article) {
        String sql = "select articles.id_article as id_article, articles.nom_article as nom_article, articles.description as description, articles.date_debut_encheres as date_debut_encheres, articles.date_fin_encheres as date_fin_encheres, articles.prix_initial as prix_initial, articles.prix_vente as prix_vente, articles.etat_vente as etat_vente, articles.photo_article as photo_article, id_vendeur, vendeurs.pseudo as pseudo_vendeur, vendeurs.nom as nom_vendeur, vendeurs.prenom as prenom_vendeur, articles.id_categorie, categories.libelle as libelle_categorie, articles.id_retrait, retraits.rue as rue_retrait, retraits.code_postal as code_postal_retrait, retraits.ville as ville_retrait, articles.id_acheteur, acheteurs.nom as nom_acheteur, acheteurs.prenom as prenom_acheteur from Articles\n " +
                "    left join utilisateurs as vendeurs on vendeurs.id_utilisateur = articles.id_vendeur\n " +
                " left join categories on categories.id_categorie = articles.id_categorie\n " +
                "left join retraits on retraits.id_retrait = articles.id_retrait\n " +
                "left join utilisateurs as acheteurs on acheteurs.id_utilisateur = articles.id_acheteur " +
                " WHERE id_article = :id";
        return namedParameterJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id_article), new ArticleRowMapper());
    }

    @Override
    public void delete(long id_article) {
        String sql = "delete from Articles where id_article=:id_article";
        //impossible d'utiliser BeanPropertySqlParameterSource il n'y a pas d'objet
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id_article", id_article);

        //lancement de la requête
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void update(Article article) {
        String sql = "update Articles set nom_article=:nom_article, description=:description, date_debut_encheres=:date_debut_encheres, date_fin_encheres=:date_fin_encheres, prix_initial=:prix_initial, prix_vente=:prix_vente, etat_vente=:etat_vente, photo_article=:photo_article,id_vendeur=:id_vendeur, id_categorie=:id_categorie, id_retrait=:id_retrait, id_acheteur=:id_acheteur where id_article=:id_article";
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
        map.addValue("id_retrait", article.getLieuxRetrait() != null ? article.getLieuxRetrait().getId_retrait() : null);
        map.addValue("photo_article", article.getPhotoArticle() != null ? article.getPhotoArticle() :"/image/encheres_marteau.jpg");

        //si il n'y a pas d'acheteur associé on met null ; l'acheteur est un nombre ici
        if (article.getAcheteur() != null) {
            map.addValue("id_acheteur", article.getAcheteur().getId_utilisateur());
        } else {
            map.addValue("id_acheteur", null);
        }

        //lancement de la requête
        namedParameterJdbcTemplate.update(sql, map);
    }

//    @Override
//    public List<Article> readAllContainsString() {
//        String sql = "select articles.id_article as id_article, articles.nom_article as nom_article, articles.description as description, articles.date_debut_encheres as date_debut_encheres, articles.date_fin_encheres as date_fin_encheres, articles.prix_initial as prix_initial, articles.prix_vente as prix_vente, articles.etat_vente as etat_vente, id_vendeur, vendeurs.nom as nom_vendeur, vendeurs.prenom as prenom_vendeur, articles.id_categorie, categories.libelle as libelle_categorie, articles.id_retrait, retraits.rue as rue_retrait, retraits.code_postal as code_postal_retrait, retraits.ville as ville_retrait, articles.id_acheteur, acheteurs.nom as nom_acheteur, acheteurs.prenom as prenom_acheteur from Articles\n " +
//                "    left join utilisateurs as vendeurs on vendeurs.id_utilisateur = articles.id_vendeur\n " +
//                " left join categories on categories.id_categorie = articles.id_categorie\n " +
//                "left join retraits on retraits.id_retrait = articles.id_retrait\n " +
//                "left join utilisateurs as acheteurs on acheteurs.id_utilisateur = articles.id_acheteur";
//
//        //ici, on crée un ArticleRowMapper pour que la requête récupère et transforme correctement les articles à partir du resultset de la requête
//        //lancement de la requète récupération de la liste d'articles qui est passée en retour
//        return jdbcTemplate.query(sql, new ArticleRowMapper());
//
//    }

    @Override
    public List<Article> readAllArticlesEnVente() {
        String sql = "select articles.id_article as id_article, articles.nom_article as nom_article, articles.description as " +
                "description, articles.date_debut_encheres as date_debut_encheres, articles.date_fin_encheres as " +
                "date_fin_encheres, articles.prix_initial as prix_initial, articles.prix_vente as prix_vente, " +
                "articles.etat_vente as etat_vente, articles.photo_article as photo_article, id_vendeur, vendeurs.pseudo as pseudo_vendeur, vendeurs.nom as nom_vendeur, " +
                "vendeurs.prenom as prenom_vendeur, articles.id_categorie, categories.libelle as libelle_categorie, " +
                "articles.id_retrait, retraits.rue as rue_retrait, retraits.code_postal as code_postal_retrait, " +
                "retraits.ville as ville_retrait, articles.id_acheteur, acheteurs.nom as nom_acheteur, acheteurs.prenom as " +
                "prenom_acheteur from Articles " +
                " left join utilisateurs as vendeurs on vendeurs.id_utilisateur = articles.id_vendeur " +
                " left join categories on categories.id_categorie = articles.id_categorie " +
                "left join retraits on retraits.id_retrait = articles.id_retrait " +
                "left join utilisateurs as acheteurs on acheteurs.id_utilisateur = articles.id_acheteur " +
                "WHERE date_debut_encheres <= GETDATE() AND date_fin_encheres >= GETDATE()";

        return jdbcTemplate.query(sql, new ArticleRowMapper());
    }

    @Override
    public List<Article> readAllArticlesEnVenteFiltreSearch(String search) {
        String mot= "%" + search + "%";
        String sql = "select articles.id_article as id_article, articles.nom_article as nom_article, articles.description as description, articles.date_debut_encheres as date_debut_encheres, articles.date_fin_encheres as date_fin_encheres, articles.prix_initial as prix_initial, articles.prix_vente as prix_vente, articles.etat_vente as etat_vente, articles.photo_article as photo_article, id_vendeur, vendeurs.pseudo as pseudo_vendeur, vendeurs.nom as nom_vendeur, vendeurs.prenom as prenom_vendeur, articles.id_categorie, categories.libelle as libelle_categorie, articles.id_retrait, retraits.rue as rue_retrait, retraits.code_postal as code_postal_retrait, retraits.ville as ville_retrait, articles.id_acheteur, acheteurs.nom as nom_acheteur, acheteurs.prenom as prenom_acheteur from Articles\n " +
                "    left join utilisateurs as vendeurs on vendeurs.id_utilisateur = articles.id_vendeur\n " +
                " left join categories on categories.id_categorie = articles.id_categorie\n " +
                "left join retraits on retraits.id_retrait = articles.id_retrait\n " +
                "left join utilisateurs as acheteurs on acheteurs.id_utilisateur = articles.id_acheteur\n " +
                "WHERE (nom_article LIKE ?)\n ";
//               + "AND date_debut_encheres <= GETDATE() AND date_fin_encheres >= GETDATE()";

        return jdbcTemplate.query(sql, new ArticleRowMapper(), mot);
    }

    @Override
    public List<Article> readAllArticlesVenteNonDebutees() {
        String sql = "select articles.id_article as id_article, articles.nom_article as nom_article, articles.description as description, articles.date_debut_encheres as date_debut_encheres, articles.date_fin_encheres as date_fin_encheres, articles.prix_initial as prix_initial, articles.prix_vente as prix_vente, articles.etat_vente as etat_vente, articles.photo_article as photo_article, id_vendeur, vendeurs.pseudo as pseudo_vendeur, vendeurs.nom as nom_vendeur, vendeurs.prenom as prenom_vendeur, articles.id_categorie, categories.libelle as libelle_categorie, articles.id_retrait, retraits.rue as rue_retrait, retraits.code_postal as code_postal_retrait, retraits.ville as ville_retrait, articles.id_acheteur, acheteurs.nom as nom_acheteur, acheteurs.prenom as prenom_acheteur from Articles\n " +
                "    left join utilisateurs as vendeurs on vendeurs.id_utilisateur = articles.id_vendeur\n " +
                " left join categories on categories.id_categorie = articles.id_categorie\n " +
                "left join retraits on retraits.id_retrait = articles.id_retrait\n " +
                "left join utilisateurs as acheteurs on acheteurs.id_utilisateur = articles.id_acheteur\n " +
                "WHERE date_debut_encheres > GETDATE()";

        return jdbcTemplate.query(sql, new ArticleRowMapper());
    }

    @Override
    public List<Article> readAllArticlesVenteTerminee() {
        String sql = "select articles.id_article as id_article, articles.nom_article as nom_article, articles.description as description, articles.date_debut_encheres as date_debut_encheres, articles.date_fin_encheres as date_fin_encheres, articles.prix_initial as prix_initial, articles.prix_vente as prix_vente, articles.etat_vente as etat_vente, articles.photo_article as photo_article, id_vendeur, vendeurs.pseudo as pseudo_vendeur, vendeurs.nom as nom_vendeur, vendeurs.prenom as prenom_vendeur, articles.id_categorie, categories.libelle as libelle_categorie, articles.id_retrait, retraits.rue as rue_retrait, retraits.code_postal as code_postal_retrait, retraits.ville as ville_retrait, articles.id_acheteur, acheteurs.nom as nom_acheteur, acheteurs.prenom as prenom_acheteur from Articles\n " +
                "    left join utilisateurs as vendeurs on vendeurs.id_utilisateur = articles.id_vendeur\n " +
                " left join categories on categories.id_categorie = articles.id_categorie\n " +
                "left join retraits on retraits.id_retrait = articles.id_retrait\n " +
                "left join utilisateurs as acheteurs on acheteurs.id_utilisateur = articles.id_acheteur\n " +
                "WHERE date_fin_encheres <= GETDATE()";

        return jdbcTemplate.query(sql, new ArticleRowMapper());
    }

    @Override
    public List<Article> readAllArticlesEnVenteByUtilisateurEnCours(long id) {
        String sql = "select articles.id_article as id_article, articles.nom_article as nom_article, articles.description as description, articles.date_debut_encheres as date_debut_encheres, articles.date_fin_encheres as date_fin_encheres, articles.prix_initial as prix_initial, articles.prix_vente as prix_vente, articles.etat_vente as etat_vente, articles.photo_article as photo_article, id_vendeur, vendeurs.pseudo as pseudo_vendeur, vendeurs.nom as nom_vendeur, vendeurs.prenom as prenom_vendeur, articles.id_categorie, categories.libelle as libelle_categorie, articles.id_retrait, retraits.rue as rue_retrait, retraits.code_postal as code_postal_retrait, retraits.ville as ville_retrait, articles.id_acheteur, acheteurs.nom as nom_acheteur, acheteurs.prenom as prenom_acheteur from Articles\n " +
                "    left join utilisateurs as vendeurs on vendeurs.id_utilisateur = articles.id_vendeur\n " +
                " left join categories on categories.id_categorie = articles.id_categorie\n " +
                "left join retraits on retraits.id_retrait = articles.id_retrait\n " +
                "left join utilisateurs as acheteurs on acheteurs.id_utilisateur = articles.id_acheteur\n " +
                "WHERE (vendeurs.id_utilisateur = :id)\n " +
                "AND date_debut_encheres <= GETDATE() AND date_fin_encheres >= GETDATE()";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", id);

        return namedParameterJdbcTemplate.query(sql, map, new ArticleRowMapper());
    }

    @Override
    public List<Article> readAllArticlesEnVenteByUtilisateurNonDebutees(long id) {
        String sql = "select articles.id_article as id_article, articles.nom_article as nom_article, articles.description as description, articles.date_debut_encheres as date_debut_encheres, articles.date_fin_encheres as date_fin_encheres, articles.prix_initial as prix_initial, articles.prix_vente as prix_vente, articles.etat_vente as etat_vente, articles.photo_article as photo_article, id_vendeur, vendeurs.pseudo as pseudo_vendeur, vendeurs.nom as nom_vendeur, vendeurs.prenom as prenom_vendeur, articles.id_categorie, categories.libelle as libelle_categorie, articles.id_retrait, retraits.rue as rue_retrait, retraits.code_postal as code_postal_retrait, retraits.ville as ville_retrait, articles.id_acheteur, acheteurs.nom as nom_acheteur, acheteurs.prenom as prenom_acheteur from Articles\n " +
                "    left join utilisateurs as vendeurs on vendeurs.id_utilisateur = articles.id_vendeur\n " +
                " left join categories on categories.id_categorie = articles.id_categorie\n " +
                "left join retraits on retraits.id_retrait = articles.id_retrait\n " +
                "left join utilisateurs as acheteurs on acheteurs.id_utilisateur = articles.id_acheteur\n " +
                "WHERE (vendeurs.id_utilisateur = :id)\n " +
        "AND date_debut_encheres > GETDATE()";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", id);

        return namedParameterJdbcTemplate.query(sql, map, new ArticleRowMapper());
    }

    @Override
    public List<Article> readAllArticlesEnVenteByUtilisateurTerminées(long id) {
        String sql = "select articles.id_article as id_article, articles.nom_article as nom_article, articles.description as description, articles.date_debut_encheres as date_debut_encheres, articles.date_fin_encheres as date_fin_encheres, articles.prix_initial as prix_initial, articles.prix_vente as prix_vente, articles.etat_vente as etat_vente, articles.photo_article as photo_article, id_vendeur, vendeurs.pseudo as pseudo_vendeur, vendeurs.nom as nom_vendeur, vendeurs.prenom as prenom_vendeur, articles.id_categorie, categories.libelle as libelle_categorie, articles.id_retrait, retraits.rue as rue_retrait, retraits.code_postal as code_postal_retrait, retraits.ville as ville_retrait, articles.id_acheteur, acheteurs.nom as nom_acheteur, acheteurs.prenom as prenom_acheteur from Articles\n " +
                "    left join utilisateurs as vendeurs on vendeurs.id_utilisateur = articles.id_vendeur\n " +
                " left join categories on categories.id_categorie = articles.id_categorie\n " +
                "left join retraits on retraits.id_retrait = articles.id_retrait\n " +
                "left join utilisateurs as acheteurs on acheteurs.id_utilisateur = articles.id_acheteur\n " +
                "WHERE (vendeurs.id_utilisateur = :id)\n " +
                "AND date_fin_encheres < GETDATE()";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", id);

        return namedParameterJdbcTemplate.query(sql, map, new ArticleRowMapper());
    }

    //recup id_articles de tous les articles en cours de vente

    @Override
    public List<Long> readAllIdArticlesEnVente() {
        String sql = "select id_article from Articles\n " +
                "WHERE date_debut_encheres <= GETDATE() AND date_fin_encheres >= GETDATE()";

        return jdbcTemplate.queryForList(sql, Long.class);
    }

    //recup id_articles de tous les articles en cours de vente sur lesquels l'utilisateur est le mieux placé

    @Override
    public List<Long> readIdArticlesMeilleureOffreUtilisateur(long id) {
        String sql = """
				SELECT DISTINCT articles.id_article
				FROM Articles 
				JOIN Encheres ON articles.id_article = encheres.id_article
				WHERE encheres.id_encherisseur = :id
				AND encheres.montant_enchere = articles.prix_vente
				""";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", id);

        return namedParameterJdbcTemplate.queryForList(sql, map, Long.class);
    }

}
