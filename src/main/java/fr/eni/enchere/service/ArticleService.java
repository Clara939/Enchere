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


    List<Article> readAllArticlesEnVenteFiltre(String search, long id_categorie, boolean encheres_ouvertes, boolean achat, boolean mes_encheres_cours, boolean mes_encheres_remportees, boolean vente, boolean mes_ventes_cours, boolean ventes_non_debutees, boolean ventes_terminees);
}
