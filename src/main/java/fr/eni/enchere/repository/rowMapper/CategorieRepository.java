package fr.eni.enchere.repository.rowMapper;

import fr.eni.enchere.bo.Categories;

import java.util.List;

public interface CategorieRepository {
    public List<Categorie> readAll();
    void create(Categorie categorie);

    Categorie getById(long id);

    void delete(long id);

    void update(Categorie categorie);
}
