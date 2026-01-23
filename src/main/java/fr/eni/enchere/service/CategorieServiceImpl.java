package fr.eni.enchere.service;

import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.repository.CategorieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieServiceImpl implements CategorieService{
    CategorieRepository categorieRepository;

    public CategorieServiceImpl(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @Override
    public List<Categorie> readAll() { return this.categorieRepository.readAll(); }

    @Override
    public void create(Categorie categorie) { this.categorieRepository.create(categorie); }

    @Override
    public Categorie readById(long id) { return this.categorieRepository.readById(id); }

    @Override
    public void delete(long id) { this.categorieRepository.delete(id); }

    @Override
    public void update(Categorie categorie) { this.categorieRepository.update(categorie); }
}
