package fr.eni.enchere.repository.rowMapper;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.bo.Utilisateur;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleRowMapper implements RowMapper<Article> {

    @Override
    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        Article articleResult = new Article();

        articleResult.setId_article(rs.getLong("id_article"));
        articleResult.setNom_article(rs.getString("nom_article"));
        articleResult.setDescription(rs.getString("description"));
        articleResult.setDate_debut_encheres(rs.getDate("date_debut_encheres").toLocalDate());
        articleResult.setDate_fin_encheres(rs.getDate("date_fin_encheres").toLocalDate());
        articleResult.setPrix_initial(rs.getInt("prix_initial"));
        articleResult.setPrix_vente(rs.getInt("prix_vente"));
        articleResult.setEtat_vente(rs.getString(("etat_vente")));

//        //gestion du prix de vente initialement null
//        int prix_vente = rs.getInt("prix_vente");
//        if(prix_vente == 0){
//            articleResult.setPrix_vente(null);
//        } else {
//            articleResult.setPrix_vente(prix_vente);
//        }

        //récupération de l'id du vendeur si il y en a un (normalement : obligatoire)
        long idVendeur = rs.getLong("id_vendeur");
        //test si il y a un vendeur
        if (idVendeur > 0) {
            //j'ai un vendeur associé à l'article : je le crée puis je remplis ses champs :
            Utilisateur vendeur = new Utilisateur();
            vendeur.setId_utilisateur(rs.getInt("id_vendeur"));
            vendeur.setPseudo(rs.getString("pseudo_vendeur"));
            vendeur.setNom(rs.getString("nom_vendeur"));
            vendeur.setPrenom(rs.getString("prenom_vendeur"));

            articleResult.setVendeur(vendeur);
        }

        //récupération de la catégorie
        long idCategorie = rs.getLong("id_categorie");
        if(idCategorie > 0) {
            Categorie categorie = new Categorie();
            categorie.setId_categorie(rs.getLong("id_categorie"));
            categorie.setLibelle(rs.getString("libelle_categorie"));

            articleResult.setCategorieArticle(categorie);
        }
            //récupération du retrait
        long idRetrait = rs.getLong("id_retrait");
        if (idRetrait > 0) {
            Retrait retrait = new Retrait();
            retrait.setId_retrait(rs.getLong("id_retrait"));
            retrait.setRue(rs.getString("rue_retrait"));
            retrait.setCode_postal(rs.getString("code_postal_retrait"));
            retrait.setVille((rs.getString("ville_retrait")));
            articleResult.setLieuxRetrait(retrait);
        }

//reconstitution de l'objet article

        //récupération de l'id de l'acheteur si il y en a un
        long idAcheteur = rs.getLong("id_acheteur");
        //test si il y a un acheteur
        Utilisateur acheteur = new Utilisateur();
        if (idAcheteur > 0) {
            //j'ai un acheteur associé à l'article : je le crée puis je remplis ses champs :
            acheteur.setId_utilisateur(rs.getInt("id_acheteur"));
            acheteur.setNom(rs.getString("nom_acheteur"));
            acheteur.setPrenom(rs.getString("prenom_acheteur"));

            articleResult.setAcheteur(acheteur);
        } else {
            articleResult.setAcheteur(null);
        }
        return articleResult;
    }
}
