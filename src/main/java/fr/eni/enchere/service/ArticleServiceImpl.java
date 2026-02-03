package fr.eni.enchere.service;

import fr.eni.enchere.bo.*;
import fr.eni.enchere.repository.ArticleRepository;
import fr.eni.enchere.repository.EnchereRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ArticleServiceImpl implements ArticleService{
    private final UtilisateurService utilisateurService;
    private final ArticleRepository articleRepository;
    private final EnchereRepository enchereRepository;
    private final RetraitService retraitService;     // ← AJOUTEZ
    private final CategorieService categorieService; // ← AJOUTEZ
    private final PhotoService photoService;

    public ArticleServiceImpl(UtilisateurService utilisateurService, ArticleRepository articleRepository, EnchereRepository enchereRepository, RetraitService retraitService, CategorieService categorieService, PhotoService photoService) {
        this.utilisateurService = utilisateurService;
        this.articleRepository = articleRepository;
        this.enchereRepository = enchereRepository;
        this.retraitService = retraitService;
        this.categorieService = categorieService;
        this.photoService = photoService;
    }

    @Override
    public List<Article> readAll() { return this.articleRepository.readAll(); }

    @Override
    public void create(Article article) {
        this.articleRepository.create(article); }

    @Override
    public Article readById(long id_article) {
        Article article = articleRepository.readById(id_article);
        if(article.getPrix_vente() == 0){
            article.setPrix_vente(article.getPrix_initial());
        }
        if (article != null) {
            mettreAJourEtatVente(article);
        }
        return article; }

    @Override
    public void delete(long id_article) { this.articleRepository.delete(id_article); }

    @Override
    public void update(Article article) { this.articleRepository.update(article); }

    @Override
    public List<Article> readAllArticlesEnVente() {
        List<Article> articleListeEnVente = articleRepository.readAllArticlesEnVente();
        for (Article a : articleListeEnVente){
            if (a.getPrix_vente() == 0){
                a.setPrix_vente(a.getPrix_initial());
            }
        }
        return articleListeEnVente;
    }

    public List<Article> readAllArticlesEnVenteFiltre(String search, long id_categorie, String radioSelectionnee, boolean encheres_ouvertes, boolean mes_encheres_cours, boolean mes_encheres_remportees, boolean mes_ventes_cours, boolean ventes_non_debutees, boolean ventes_terminees) {

        List<Long> idArticleListeAchatTotale = new ArrayList<>();
        List<Article> articleListeVenteTotale = new ArrayList<>();
        List<Article> articleListeFiltreRecherche = new ArrayList<>();
        List<Article> articleListeFiltre = new ArrayList<>();
        List<Long> idArticleListeEncheresOuvertes = new ArrayList<>();

        articleListeFiltreRecherche = articleRepository.readAllArticlesEnVenteFiltreSearch(search);
        if (!(id_categorie == 0)){ //si une categorie est choisie, on filtre la première liste
            articleListeFiltreRecherche = articleListeFiltreRecherche.stream()
                    .filter(a -> a.getCategorieArticle().getId_categorie() == id_categorie)
                    .collect(Collectors.toList());
        }
        List<Long> idArticleRechercheListe = articleListeFiltreRecherche.stream()
                .map(a -> a.getId_article())
                .collect(Collectors.toList());

        //test si l'utilisateur est connecté. si non, on renvoit les résultats sans passer par les autres filtres
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || radioSelectionnee == null){
            idArticleListeEncheresOuvertes = articleRepository.readAllIdArticlesEnVente();
            idArticleRechercheListe.retainAll(idArticleListeEncheresOuvertes);
            articleListeFiltre = idArticleRechercheListe.stream()
                    .map(l -> articleRepository.readById(l))
                    .collect(Collectors.toList());

            return articleListeFiltre;
        }
        long idUtilisateurActif = utilisateurService.recuperationIdUtilisateurActif().getId_utilisateur();
        if ("achat".equals(radioSelectionnee)) {

            List<Long> idArticleListeEncheresEnCours = new ArrayList<>();
            List<Long> idArticleListeEncheresEmportees = new ArrayList<>();
            if (encheres_ouvertes) {
                //Liste des articles actuellement en vente
            }
            idArticleListeEncheresOuvertes = articleRepository.readAllIdArticlesEnVente();

            if (mes_encheres_cours) {
                //Liste des articles sur lesquels l'utilisateur a fait au moins une enchère
                idArticleListeEncheresEnCours = enchereRepository.readAllForOneUtilisateurVenteEnCours(idUtilisateurActif);
            }
            if (mes_encheres_remportees) {
                //Liste des articles pour lesquels la meilleure anchère est faite par l'utilisateur
                idArticleListeEncheresEmportees = articleRepository.readIdArticlesMeilleureOffreUtilisateur(idUtilisateurActif);

            }
            idArticleListeAchatTotale.addAll(idArticleListeEncheresOuvertes);
            idArticleListeAchatTotale.addAll(idArticleListeEncheresEnCours);
            idArticleListeAchatTotale.addAll(idArticleListeEncheresEmportees);
            idArticleListeAchatTotale = idArticleListeAchatTotale.stream().distinct().collect(Collectors.toList());
            idArticleRechercheListe.retainAll(idArticleListeAchatTotale);
        }

        if ("vente".equals(radioSelectionnee)) {
            List<Article> articleListeVente = new ArrayList<>();
            List<Article> articleListeVenteNonDeb = new ArrayList<>();
            List<Article> articleListeTerminees = new ArrayList<>();
            if (mes_ventes_cours) {
                articleListeVente = articleRepository.readAllArticlesEnVenteByUtilisateurEnCours(idUtilisateurActif);
            }
            if (ventes_non_debutees) {
                articleListeVenteNonDeb = articleRepository.readAllArticlesEnVenteByUtilisateurNonDebutees(idUtilisateurActif);
            }
            if (ventes_terminees) {
                articleListeTerminees = articleRepository.readAllArticlesEnVenteByUtilisateurTerminées(idUtilisateurActif);
            }
            ;

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

        for (Article a : articleListeFiltre){
            if (a.getPrix_vente() == 0){
                a.setPrix_vente(a.getPrix_initial());
            }
        }

        return articleListeFiltre;
    }
    //    met a jour l'etat de vente selon dates et prix_vente
    public void mettreAJourEtatVente(Article article){
        if (article == null || article.getDate_debut_encheres() == null || article.getDate_fin_encheres() == null){
            return;
        }
        LocalDate aujourdhui = LocalDate.now();
        LocalDate debut = article.getDate_debut_encheres();
        LocalDate fin = article.getDate_fin_encheres();

//          CREE
        if (aujourdhui.isBefore(debut)){
            article.setEtat_vente("CREE");
        }

//        EN_VENTE
        else if (aujourdhui.isAfter(debut) || aujourdhui.equals(debut)) {
            if (aujourdhui.isBefore(fin) || aujourdhui.isEqual(fin)){
                article.setEtat_vente("EN_VENTE");
            }
        }

//        VENDU ou NON_VENDU
        else {
            if (article.getPrix_vente() == null || article.getPrix_vente() == 0){
                article.setEtat_vente("NON_VENDU");
            }
            else {
                article.setEtat_vente("VENDU");
            }
        }
        update(article);
    }

//    creation de l'article complet
    @Override
    @Transactional
    public Article creerArticleComplet(Article article, Long categorieId, MultipartFile photoArticle) {

        try {
        // 1. Vendeur connecté
        Utilisateur vendeur = utilisateurService.recuperationIdUtilisateurActif();
        article.setVendeur(vendeur);

        // 2. Catégorie
        Categorie categorie = categorieService.readById(categorieId);
        article.setCategorieArticle(categorie);
        article.setAcheteur(null);

        //  3. RETRAIT
        Retrait retrait = new Retrait();
        retrait.setRue(vendeur.getRue());
        retrait.setCode_postal(vendeur.getCode_postal());
        retrait.setVille(vendeur.getVille());
        retraitService.createRetrait(retrait);  // ← GÉNÈRE id_retrait
        article.setLieuxRetrait(retrait);

        // 4.  Article (avec id_retrait valide)
        this.create(article);


        // 5. Photo avec ID réel
        if (photoArticle != null && !photoArticle.isEmpty()) {
            String urlPhoto = photoService.SaveArticlePhoto(photoArticle, article.getId_article());
            article.setPhotoArticle(urlPhoto);
            this.update(article);
        }

        mettreAJourEtatVente(article);
        return article;
    }
    catch (Exception e) {
        System.err.println("ERREUR FATALE: " + e.getMessage());
        e.printStackTrace();
        throw e;
    }
    }
}
