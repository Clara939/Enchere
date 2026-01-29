package fr.eni.enchere.service;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.repository.ArticleRepository;
import fr.eni.enchere.repository.EnchereRepository;
import fr.eni.enchere.repository.EnchereRepositorySql;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService{
   EnchereRepository enchereRepository;
    private final UtilisateurService utilisateurService;
    ArticleRepository articleRepository;

    public ArticleServiceImpl(UtilisateurService utilisateurService, EnchereRepository enchereRepository, ArticleRepository articleRepository) {
        this.utilisateurService = utilisateurService;
        this.enchereRepository = enchereRepository;
        this.articleRepository = articleRepository;
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
    public List<Article> readAllArticlesEnVenteFiltre(String search, long id_categorie, boolean encheres_ouvertes) {
        List<Article> articleListeFiltre = articleRepository.readAllArticlesEnVenteFiltreSearch(search);
        if (!(id_categorie == 0)){ //si une categorie est choisie, on filtre la première liste
            articleListeFiltre = articleListeFiltre.stream()
                    .filter(a -> a.getCategorieArticle().getId_categorie() == id_categorie)
                    .collect(Collectors.toList());
        }
        if (encheres_ouvertes){
            articleListeFiltre = articleListeFiltre.stream()
                    .filter((a -> a.getVendeur().getId_utilisateur() != utilisateurService.recuperationIdUtilisateurActif().getId_utilisateur()))
                    .collect(Collectors.toList());
        }
        return articleListeFiltre;
    }

}
