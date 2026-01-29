package fr.eni.enchere.service;

import fr.eni.enchere.bo.Enchere;

import java.util.List;

public interface EnchereService {
    void createEnchere(Enchere enchere);

    List<Enchere> readAll();

    Enchere readById(long id);

    void updateEnchere(Enchere enchere);

    void deleteEnchere(long id);

/*///-------------Méthode pour faire une enchère------------------
    boolean faireEnchere(long idArticle, int montantPropose, long idEncherisseur);

    Enchere getMeilleureEncherePourArticle(long idArticle);

    boolean verifierEncherePossible(long idArticle, int montantPropose, long idEncherisseur);

    int getPrixActuelArtcle(long idArticle);
*/
}
