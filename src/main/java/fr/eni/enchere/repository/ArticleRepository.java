package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;

import java.util.List;

public interface ArticleRepository {
    public List<Article> readAll();
    void create(Article article);

    Article readById(long id);

    void delete(long id);

    void update(Article article);

    List<Article> readAllArticlesEnVente();

    List<Article> readAllArticlesEnVenteFiltreSearch(String search);

    List<Article> readAllArticlesVenteNonDebutees();

    List<Article> readAllArticlesVenteTerminee();

    List<Article> readAllArticlesEnVenteByUtilisateurEnCours(long id);

    List<Article> readAllArticlesEnVenteByUtilisateurNonDebutees(long id);

    List<Article> readAllArticlesEnVenteByUtilisateurTerminées(long id);

    List<Long> readAllIdArticlesEnVente();

    List<Long> readIdArticlesMeilleureOffreUtilisateur(long id);
}
