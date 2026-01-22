package fr.eni.enchere.repository.rowMapper;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategorieRepositorySQL implements CategorieRepository{
    @Override
    public List<Categorie> readAll() {
        return List.of();
    }

    @Override
    public void create(Categorie categorie) {

    }

    @Override
    public Categorie getById(long id) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void update(Categorie categorie) {

    }
}
