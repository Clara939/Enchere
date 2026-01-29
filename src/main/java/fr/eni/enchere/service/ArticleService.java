package fr.eni.enchere.service;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;

import java.util.List;

public interface ArticleService {

    public List<Article> readAll();

    void create(Article article);

    Article readById(long id);

    void delete(long id);

    void update(Article article);

    List<Article> readAllArticlesEnVente();


    List<Article> readAllArticlesEnVenteFiltre(String search, long id_categorie, boolean encheres_ouvertes);
}
