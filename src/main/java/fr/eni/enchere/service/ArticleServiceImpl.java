package fr.eni.enchere.service;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.repository.ArticleRepository;
import fr.eni.enchere.repository.EnchereRepositorySql;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService{
    private final EnchereRepositorySql enchereRepositorySql;
    ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, EnchereRepositorySql enchereRepositorySql) {
        this.articleRepository = articleRepository;
        this.enchereRepositorySql = enchereRepositorySql;
    }

    @Override
    public List<Article> readAll() { return this.articleRepository.readAll(); }

    @Override
    public void create(Article article) {
        this.articleRepository.create(article); }

    @Override
    public Article readById(long id_article) { return this.articleRepository.readById(id_article); }

    @Override
    public void delete(long id_article) { this.articleRepository.delete(id_article); }

    @Override
    public void update(Article article) { this.articleRepository.update(article); }

    @Override
    public List<Article> readAllArticlesEnVente() {
        List<Article> articleListeEnVente = articleRepository.readAllArticlesEnVente();
        for (Article a : articleListeEnVente){
            if (a.getPrix_vente() == null){
                a.setPrix_vente(a.getPrix_initial());
            }
        }
        return articleListeEnVente;
    }

    @Override
    public List<Article> readAllArticlesEnVenteFiltre(String search, long id_categorie) {
        List<Article> articleListeFiltre = articleRepository.readAllArticlesEnVenteFiltreSearch(search);
        if (id_categorie == 0){ //si aucune categorie n'est choisie, id_categorie = 0
            return articleListeFiltre;
        }
        return articleListeFiltre.stream()
                .filter(a -> a.getCategorieArticle().getId_categorie() == id_categorie)
                .collect(Collectors.toList());
    }

}
