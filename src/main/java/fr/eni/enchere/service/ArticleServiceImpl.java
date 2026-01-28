package fr.eni.enchere.service;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{
    ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> readAll() { return this.articleRepository.readAll(); }

    @Override
    public void create(Article article) { this.articleRepository.create(article); }

    @Override
    public Article readById(long id) { return this.articleRepository.readById(id); }

    @Override
    public void delete(long id) { this.articleRepository.delete(id); }

    @Override
    public void update(Article article) { this.articleRepository.update(article); }

    @Override
    public List<Article> readAllArticlesEnVente() { return this.articleRepository.readAllArticlesEnVente(); }
}
