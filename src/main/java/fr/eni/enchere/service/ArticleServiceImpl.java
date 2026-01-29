package fr.eni.enchere.service;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.repository.ArticleRepository;
import fr.eni.enchere.repository.EnchereRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    public List<Article> readAllArticlesEnVenteFiltre(String search, long id_categorie, boolean encheres_ouvertes, boolean achat, boolean mes_encheres_cours, boolean mes_encheres_remportees, boolean vente, boolean mes_ventes_cours, boolean ventes_non_debutees, boolean ventes_terminees) {
        List<Article> articleListeFiltre = articleRepository.readAllArticlesEnVenteFiltreSearch(search);
        if (!(id_categorie == 0)){ //si une categorie est choisie, on filtre la première liste
            articleListeFiltre = articleListeFiltre.stream()
                    .filter(a -> a.getCategorieArticle().getId_categorie() == id_categorie)
                    .collect(Collectors.toList());
        }
        if(achat){
            if (encheres_ouvertes){
                articleListeFiltre = articleListeFiltre.stream()
                        .filter((a -> a.getVendeur().getId_utilisateur() != utilisateurService.recuperationIdUtilisateurActif().getId_utilisateur()))
                        .collect(Collectors.toList());
            }
            if(mes_encheres_cours){
                //Liste des articles sur lesquels l'utilisateur a fait au moins une enchère
                articleListeFiltre = getArticlesEncheris();
//                List<Enchere> enchereListe = enchereRepository.readAllForOneUtilisateur(utilisateurService.recuperationIdUtilisateurActif().getId_utilisateur());
//                 Set<Long> idArticleSet = enchereListe.stream()
//                        .map(e -> e.getArticles().getId_article())
//                        .collect(Collectors.toSet());
//
//                articleListeFiltre = idArticleSet.stream()
//                        .map(id -> articleRepository.readById(id))
//                        .collect(Collectors.toList());
            }
            if (mes_encheres_remportees){
                articleListeFiltre = new ArrayList<>();
                List <Article> articleListepreFiltre = getArticlesEncheris();
                for (Article a : articleListepreFiltre){
                    List<Enchere> enchereList = enchereRepository.readAllForOneArticle(a.getId_article());
                    if (!enchereList.isEmpty()){
                        Enchere gagnante = enchereList.get(0);
                        Long idgagnant = gagnante.getEncherisseur().getId_utilisateur();
                        if (idgagnant == utilisateurService.recuperationIdUtilisateurActif().getId_utilisateur()){
                            articleListeFiltre.add(a);
                        }
                    }
                }
            }
                    }
        if(vente){
            if (mes_ventes_cours){
                articleListeFiltre = articleListeFiltre.stream()
                        .filter(a -> a.getVendeur().getId_utilisateur() == utilisateurService.recuperationIdUtilisateurActif().getId_utilisateur())
                        .collect(Collectors.toList());
            }
            if (ventes_non_debutees){
                articleListeFiltre = articleRepository.readAllArticlesVenteNonDebutees().stream()
                        .filter(a -> a.getVendeur().getId_utilisateur() == utilisateurService.recuperationIdUtilisateurActif().getId_utilisateur())
                        .collect(Collectors.toList());
            }
            if (ventes_terminees){
                articleListeFiltre = articleRepository.readAllArticlesVenteTerminee().stream()
                        .filter(a -> a.getVendeur().getId_utilisateur() == utilisateurService.recuperationIdUtilisateurActif().getId_utilisateur())
                        .collect(Collectors.toList());
            }
        }

        return articleListeFiltre;
    }

    private List<Article> getArticlesEncheris() {
        List<Article> articleListeFiltre;
        List<Enchere> enchereListe = enchereRepository.readAllForOneUtilisateur(utilisateurService.recuperationIdUtilisateurActif().getId_utilisateur());
        Set<Long> idArticleSet = enchereListe.stream()
                .map(e -> e.getArticles().getId_article())
                .collect(Collectors.toSet());

        articleListeFiltre = idArticleSet.stream()
                .map(id -> articleRepository.readById(id))
                .collect(Collectors.toList());
        return articleListeFiltre;
    }

}
