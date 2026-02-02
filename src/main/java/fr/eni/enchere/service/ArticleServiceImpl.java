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

//    @Override
//    public List<Article> readAllArticlesEnVenteFiltre(String search, long id_categorie, String radioSelectionnee, boolean encheres_ouvertes, boolean mes_encheres_cours, boolean mes_encheres_remportees, boolean mes_ventes_cours, boolean ventes_non_debutees, boolean ventes_terminees) {
//        long idUtilisateurActif = utilisateurService.recuperationIdUtilisateurActif().getId_utilisateur();
//        List<Article> articleListeAchatTotale = new ArrayList<>();
//        List<Article> articleListeVenteTotale = new ArrayList<>();
//        List<Article> articleListeFiltreRecherche = new ArrayList<>();
//        List<Article> articleListeFiltre = new ArrayList<>();
//        List<Long> intersection = new ArrayList<>();
//
//        articleListeFiltreRecherche = articleRepository.readAllArticlesEnVenteFiltreSearch(search);
//        if (!(id_categorie == 0)){ //si une categorie est choisie, on filtre la première liste
//            articleListeFiltreRecherche = articleListeFiltreRecherche.stream()
//                    .filter(a -> a.getCategorieArticle().getId_categorie() == id_categorie)
//                    .collect(Collectors.toList());
//        }
//        List<Long> idArticleRechercheListe = articleListeFiltreRecherche.stream()
//                .map(a -> a.getId_article())
//                .collect(Collectors.toList());
//
//        if("achat".equals(radioSelectionnee)){
//            List<Article> articleListeEncheresOuvertes = new ArrayList<>();
//            List<Article> articleListeEncheresEnCours = new ArrayList<>();
//            List<Article> articleListeEncheresEmportees = new ArrayList<>();
//            if (encheres_ouvertes){
//                articleListeEncheresOuvertes = articleRepository.readAllArticlesEnVente().stream()
//                        .filter((a -> a.getVendeur().getId_utilisateur() != idUtilisateurActif))
//                        .collect(Collectors.toList());
//                System.out.println("🔍 DEBUG - Enchères ouvertes: " + articleListeEncheresOuvertes.size());
//            }
//            if(mes_encheres_cours){
//                //Liste des articles sur lesquels l'utilisateur a fait au moins une enchère
//                articleListeEncheresEnCours = getArticlesEncheris();
//                System.out.println("🔍 DEBUG - Mes enchères en cours: " + articleListeEncheresEnCours.size());
////                List<Enchere> enchereListe = enchereRepository.readAllForOneUtilisateur(utilisateurService.recuperationIdUtilisateurActif().getId_utilisateur());
////                 Set<Long> idArticleSet = enchereListe.stream()
////                        .map(e -> e.getArticles().getId_article())
////                        .collect(Collectors.toSet());
////
////                articleListeFiltre = idArticleSet.stream()
////                        .map(id -> articleRepository.readById(id))
////                        .collect(Collectors.toList());
////                Set<Long> articlesEncheris = getArticlesEncheris().stream()
////                        .map(Article::getId_article)
////                        .collect(Collectors.toSet());
//
////                articleListeFiltre = articleListeFiltre.stream()
////                        .filter(a -> articlesEncheris.contains(a.getId_article()))
////                        .collect(Collectors.toList());
//            }
//            if (mes_encheres_remportees){
////                articleListeEncheresEmportees = new ArrayList<>();
//                List <Article> articleListepreFiltre = getArticlesEncheris();
//                for (Article a : articleListepreFiltre){
//                    List<Enchere> enchereList = enchereRepository.readAllForOneArticle(a.getId_article());
//                    if (!enchereList.isEmpty()){
//                        Enchere gagnante = enchereList.get(0);
//                        Long idgagnant = gagnante.getEncherisseur().getId_utilisateur();
//                        if (idgagnant == utilisateurService.recuperationIdUtilisateurActif().getId_utilisateur()){
//                            articleListeEncheresEmportees.add(a);
//                        }
//                    }
//                }
//            }
//            articleListeAchatTotale.addAll(articleListeEncheresOuvertes);
//            articleListeAchatTotale.addAll(articleListeEncheresEnCours);
//            articleListeAchatTotale.addAll(articleListeEncheresEmportees);
//            articleListeAchatTotale = articleListeAchatTotale.stream().distinct().collect(Collectors.toList());
////            articleListeAchatTotale.retainAll(articleListeFiltreRecherche);
////            return articleListeAchatTotale;
//            List<Long> idArticleListe = articleListeAchatTotale.stream()
//                    .map(a -> a.getId_article())
//                    .collect(Collectors.toList());
//            for(long i : idArticleListe){
//                if (!intersection.contains(i)){
//                    intersection.add(i);
//                }
//            }
////            intersection = idArticleRechercheListe.stream()
////                    .filter(idArticleListe::contains)
////                    .collect(Collectors.toList());
////            idArticleRechercheListe.addAll(idArticleListe);
////            idArticleRechercheListe.stream().distinct().collect(Collectors.toList());
//        }
//        if("vente".equals(radioSelectionnee)){
//            List<Article> articleListeVente = new ArrayList<>();
//            List<Article> articleListeVenteNonDeb = new ArrayList<>();
//            List<Article> articleListeTerminees = new ArrayList<>();
//            if (mes_ventes_cours){
//                articleListeVente = articleRepository.readAllArticlesEnVenteByUtilisateurEnCours(idUtilisateurActif);
//            }
//            if (ventes_non_debutees){
////                articleListeFiltre = articleRepository.readAllArticlesVenteNonDebutees().stream()
////                        .filter(a -> a.getVendeur().getId_utilisateur() == utilisateurService.recuperationIdUtilisateurActif().getId_utilisateur())
////                        .collect(Collectors.toList());
//                articleListeVenteNonDeb = articleRepository.readAllArticlesEnVenteByUtilisateurNonDebutees(idUtilisateurActif);
//            }
//            if (ventes_terminees){
////                articleListeFiltre = articleRepository.readAllArticlesVenteTerminee().stream()
////                        .filter(a -> a.getVendeur().getId_utilisateur() == utilisateurService.recuperationIdUtilisateurActif().getId_utilisateur())
////                        .collect(Collectors.toList());
//                articleListeTerminees = articleRepository.readAllArticlesEnVenteByUtilisateurTerminées(idUtilisateurActif);
//            };
//
//            articleListeVenteTotale.addAll(articleListeVente);
//            articleListeVenteTotale.addAll(articleListeVenteNonDeb);
//            articleListeVenteTotale.addAll(articleListeTerminees);
////            articleListeVenteTotale.retainAll(articleListeFiltreRecherche);
////            return articleListeVenteTotale;
//List<Long> idArticleVenteListe = articleListeVenteTotale.stream()
//        .map(a -> a.getId_article())
//        .collect(Collectors.toList());
//            for(long i : idArticleVenteListe){
//                if (!intersection.contains(i)){
//                    intersection.add(i);
//                }
//            }
    ////            intersection = idArticleRechercheListe.stream()
    ////                    .filter(idArticleVenteListe::contains)
    ////                    .collect(Collectors.toList());
    ////            idArticleRechercheListe.addAll(idArticleVenteListe);
    ////            idArticleRechercheListe.stream().distinct().collect(Collectors.toList());
//        }
//
//        articleListeFiltre = intersection.stream()
//                .map(l -> articleRepository.readById(l))
//                .collect(Collectors.toList());
//
//        return articleListeFiltre;
//    }
//
//    private List<Article> getArticlesEncheris() {
//        List<Article> articleListeFiltre;
//        List<Enchere> enchereListe = enchereRepository.readAllForOneUtilisateur(utilisateurService.recuperationIdUtilisateurActif().getId_utilisateur());
//        Set<Long> idArticleSet = enchereListe.stream()
//                .map(e -> e.getArticles().getId_article())
//                .collect(Collectors.toSet());
//
//        articleListeFiltre = idArticleSet.stream()
//                .map(id -> articleRepository.readById(id))
//                .collect(Collectors.toList());
//        return articleListeFiltre;
//    }

    public List<Article> readAllArticlesEnVenteFiltre(String search, long id_categorie, String radioSelectionnee, boolean encheres_ouvertes, boolean mes_encheres_cours, boolean mes_encheres_remportees, boolean mes_ventes_cours, boolean ventes_non_debutees, boolean ventes_terminees) {
        long idUtilisateurActif = utilisateurService.recuperationIdUtilisateurActif().getId_utilisateur();
        List<Long> idArticleListeAchatTotale = new ArrayList<>();
        List<Article> articleListeVenteTotale = new ArrayList<>();
        List<Article> articleListeFiltreRecherche = new ArrayList<>();
        List<Article> articleListeFiltre = new ArrayList<>();
        List<Long> intersection = new ArrayList<>();

        articleListeFiltreRecherche = articleRepository.readAllArticlesEnVenteFiltreSearch(search);
        if (!(id_categorie == 0)){ //si une categorie est choisie, on filtre la première liste
            articleListeFiltreRecherche = articleListeFiltreRecherche.stream()
                    .filter(a -> a.getCategorieArticle().getId_categorie() == id_categorie)
                    .collect(Collectors.toList());
        }
        List<Long> idArticleRechercheListe = articleListeFiltreRecherche.stream()
                .map(a -> a.getId_article())
                .collect(Collectors.toList());

        if("achat".equals(radioSelectionnee)){
            List<Long> idArticleListeEncheresOuvertes = new ArrayList<>();
            List<Long> idArticleListeEncheresEnCours = new ArrayList<>();
            List<Long> idArticleListeEncheresEmportees = new ArrayList<>();
            if (encheres_ouvertes){
                //Liste des articles actuellement en vente
            }
            idArticleListeEncheresOuvertes = articleRepository.readAllIdArticlesEnVente();
            System.out.println("🔍 DEBUG - Enchères ouvertes: " + idArticleListeEncheresOuvertes.size());

            if(mes_encheres_cours){
                //Liste des articles sur lesquels l'utilisateur a fait au moins une enchère
                idArticleListeEncheresEnCours = enchereRepository.readAllidArticleForOneUtilisateur(idUtilisateurActif);
                System.out.println("🔍 DEBUG - Mes enchères en cours: " + idArticleListeEncheresEnCours.size());
            }
            if (mes_encheres_remportees){
                //Liste des articles pour lesquels la meilleure anchère est faite par l'utilisateur
                idArticleListeEncheresEmportees = articleRepository.readIdArticlesMeilleureOffreUtilisateur(idUtilisateurActif);

            }
            idArticleListeAchatTotale.addAll(idArticleListeEncheresOuvertes);
            idArticleListeAchatTotale.addAll(idArticleListeEncheresEnCours);
            idArticleListeAchatTotale.addAll(idArticleListeEncheresEmportees);
            idArticleListeAchatTotale = idArticleListeAchatTotale.stream().distinct().collect(Collectors.toList());
            idArticleRechercheListe.retainAll(idArticleListeAchatTotale);
        }

        if("vente".equals(radioSelectionnee)){
            List<Article> articleListeVente = new ArrayList<>();
            List<Article> articleListeVenteNonDeb = new ArrayList<>();
            List<Article> articleListeTerminees = new ArrayList<>();
            if (mes_ventes_cours){
                articleListeVente = articleRepository.readAllArticlesEnVenteByUtilisateurEnCours(idUtilisateurActif);
            }
            if (ventes_non_debutees){
                articleListeVenteNonDeb = articleRepository.readAllArticlesEnVenteByUtilisateurNonDebutees(idUtilisateurActif);
            }
            if (ventes_terminees){
                articleListeTerminees = articleRepository.readAllArticlesEnVenteByUtilisateurTerminées(idUtilisateurActif);
            };

            articleListeVenteTotale.addAll(articleListeVente);
            articleListeVenteTotale.addAll(articleListeVenteNonDeb);
            articleListeVenteTotale.addAll(articleListeTerminees);
            //recuperation des id de chaque article car c'est par cet attribut qu'on compare les articles
            List<Long> idArticleVenteListe = articleListeVenteTotale.stream()
                    .map(a -> a.getId_article()).distinct()
                    .collect(Collectors.toList());
            idArticleRechercheListe.retainAll(idArticleVenteListe);
        }

        articleListeFiltre = idArticleRechercheListe.stream()
                .map(l -> articleRepository.readById(l))
                .collect(Collectors.toList());

        return articleListeFiltre;
    }

}
