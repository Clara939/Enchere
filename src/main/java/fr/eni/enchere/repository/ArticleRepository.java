package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;

import java.util.List;

public interface ArticleRepository {
    public List<Article> readAll();
    void create(Article article);

    Categorie readById(long id);

    void delete(long id);

    void update(Article article);
}
