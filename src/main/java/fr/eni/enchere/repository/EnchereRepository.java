package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Enchere;

import java.util.List;

public interface EnchereRepository {
    void createEnchere(Enchere enchere);

    List<Enchere> readAll();

    Enchere readById(long id);

    void updateEnchere(Enchere enchere);

    void deleteEnchere(long id);

    List<Enchere> readAllForOneArticle(long id);

    List<Enchere> readAllForOneUtilisateur(long id);

    List<Long> readAllidArticleForOneUtilisateur(long id);

    // dernière enchère (la plus élevée) Renvoie la dernière enchère
    Enchere findTopEnchereByArticle(long idArticle);


    List<Long> readAllForOneUtilisateurVenteEnCours(long id);

    List<Long> readAllidUtilisateurByIdArticle(long id);

    List<Long> readAllByIdUtilisateurAndByIdArticle(long id_utilisateur, long id_article);
}
