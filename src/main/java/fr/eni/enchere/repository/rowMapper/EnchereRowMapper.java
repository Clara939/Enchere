package fr.eni.enchere.repository.rowMapper;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.bo.Utilisateur;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EnchereRowMapper implements RowMapper<Enchere> {

    @Override
    public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
        Enchere enchereResult = new Enchere();

        enchereResult.setId_enchere(rs.getLong("id_enchere"));
        enchereResult.setDate_enchere(LocalDate.from(rs.getTimestamp("date_enchere").toLocalDateTime()));
        enchereResult.setMontant_enchere(rs.getInt("montant_enchere"));

//récupération de l'encherisseur
        long idEncherisseur = rs.getLong("id_encherisseur");
        if(idEncherisseur > 0) {
            //j'ai un encherisseur associé à l'enchere : je le crée puis je remplis ses champs :
            Utilisateur encherisseur = new Utilisateur();
            encherisseur.setId_utilisateur(rs.getLong("id_encherisseur"));
            encherisseur.setNom(rs.getNString("nom_encherisseur"));
            encherisseur.setPrenom(rs.getString("prenom_encherisseur"));
            encherisseur.setEmail(rs.getString("email_encherisseur"));
            //... Je compléter les autres champs si nécessaire apres

            enchereResult.setEncherisseur(encherisseur);
        }
//récupération de l'article
        long idArticle = rs.getLong("id_article");
            if(idArticle > 0) {
                Article article = new Article();
                article.setId_article(rs.getLong("id_article"));
                article.setNom_article(rs.getString("nom_article"));
                article.setDescription(rs.getString("description"));
                article.setPrix_vente(rs.getInt("prix_vente"));
                article.setEtat_vente(rs.getString("etat_vente"));
                //... Je compléter les autres champs si nécessaire apres
                //gestion des dates qui peuvent être nulles
                if(rs.getDate("date_debut_encheres") != null) {
                    article.setDate_debut_encheres(rs.getDate("date_debut_encheres").toLocalDate());
                }

                if(rs.getDate("date_fin_encheres") != null) {
                    article.setDate_fin_encheres(rs.getDate("date_fin_encheres").toLocalDate());
                }

                enchereResult.setArticles(article);
            }
        return enchereResult;
    }
}
