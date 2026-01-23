package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Enchere;

import java.util.List;

public interface EnchereRepository {
    void createEnchere(Enchere enchere);

    List<Enchere> readAll();

    Enchere readById(long id);

    void updateEnchere(Enchere enchere);

    void deleteEnchere(long id);
}
