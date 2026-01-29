package fr.eni.enchere.service;

import fr.eni.enchere.bo.Enchere;

import java.util.List;

public interface EnchereService {
    void createEnchere(Enchere enchere);

    List<Enchere> readAll();

    Enchere readById(long id);

    void updateEnchere(Enchere enchere);

    void deleteEnchere(long id);

//  Méthode pour faire une enchère
    void placeEnchere(long idArticle, long idUtilisateur, int montantPropose) throws Exception;
}
