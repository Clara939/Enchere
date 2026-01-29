package fr.eni.enchere.service;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.repository.ArticleRepository;
import fr.eni.enchere.repository.ArticleRepositorySQL;
import fr.eni.enchere.repository.EnchereRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService{
    private final ArticleRepositorySQL articleRepositorySQL;
    private final ArticleService articleService;
    private final UtilisateurService utilisateurService;
    EnchereRepository enchereRepository;

    public EnchereServiceImpl(EnchereRepository enchereRepository, ArticleRepositorySQL articleRepositorySQL, ArticleService articleService, UtilisateurService utilisateurService) {
        this.enchereRepository = enchereRepository;
        this.articleRepositorySQL = articleRepositorySQL;
        this.articleService = articleService;
        this.utilisateurService = utilisateurService;
    }

    @Override
    public void createEnchere(Enchere enchere) { this.enchereRepository.createEnchere(enchere); }

    @Override
    public List<Enchere> readAll() { return this.enchereRepository.readAll(); }

    @Override
    public Enchere readById(long id) { return this.enchereRepository.readById(id); }

    @Override
    public void updateEnchere(Enchere enchere) { this.enchereRepository.updateEnchere(enchere); }

    @Override
    public void deleteEnchere(long id) { this.enchereRepository.deleteEnchere(id); }

// Logique de base d'un enchere (Raman)
    @Override
    public void placeEnchere(long idArticle, long idUtilisateur, int montantPropose) throws Exception {
        Article article = articleService.readById(idArticle);

        // Vérifier si l'article existe
        if (article == null) {
            throw new Exception("Article introvable.");
        }

        // Récupération d'un utilisateur à partir de la base de données
        Utilisateur encherisseur = utilisateurService.readById(idUtilisateur);

        // Vérifier si l'utilisateur existe
        if (encherisseur == null) {
            throw new Exception("Utilisateur introuvable.");
        }



    }



}
