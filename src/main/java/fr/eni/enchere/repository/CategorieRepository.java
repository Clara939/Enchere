package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Categorie;

import java.util.List;

public interface CategorieRepository {
    public List<Categorie> readAll();
    void create(Categorie categorie);

    Categorie readById(long id);

    void delete(long id);

    void update(Categorie categorie);
}
