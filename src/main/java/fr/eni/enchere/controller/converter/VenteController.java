package fr.eni.enchere.controller.converter;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VenteController {
    UtilisateurService utilisateurService;
    ArticleService articleService;
    CategorieService categorieService;
    RetraitService retraitService;
    EnchereService enchereService;

    public VenteController(UtilisateurService utilisateurService, ArticleService articleService, CategorieService categorieService, RetraitService retraitService, EnchereService enchereService) {
        this.utilisateurService = utilisateurService;
        this.articleService = articleService;
        this.categorieService = categorieService;
        this.retraitService = retraitService;
        this.enchereService = enchereService;
    }

<<<<<<< HEAD
    @GetMapping("/encheres/details")
    public String afficherDetail(@RequestParam(name = "id") long id, Model model){
        Article article = articleService.readById(id);
=======
    @GetMapping("/encheres")
    public String afficherEncheres(Model model){
        List<Article> articleList = articleService.readAllArticlesEnVente();
        List<Categorie> categorieList = categorieService.readAll();
model.addAttribute("articleList", articleList);
model.addAttribute("categorieList", categorieList);
        model.addAttribute("id_categorie_selectionnee", 0); //affichage avec toutes catégories lors de la 1e arrivée sur la page
        model.addAttribute("search", ""); //affichage avec une recherche "vide" lors de la 1e arrivée sur la page
        return "encheres";
    }

// page nouvelle vente ( création de l'article a mettre en ventes)
    @GetMapping("/encheres/add")
    public String nouvelleVente(Model model){
        //utilisateur connecté
        Utilisateur utilisateurConnecte = utilisateurService.recuperationIdUtilisateurActif();
        //nouvel article
        Article article = new Article();

        //pré-remplit retrait avec infos utilisateur connecté
        if (utilisateurConnecte != null){
            Retrait retraitVendeur = new Retrait();
            retraitVendeur.setRue(utilisateurConnecte.getRue());
            retraitVendeur.setCode_postal(utilisateurConnecte.getCode_postal());
            retraitVendeur.setVille(utilisateurConnecte.getVille());
            article.setLieuxRetrait(retraitVendeur);
            article.setVendeur(utilisateurConnecte);
        }
>>>>>>> 516bc663ef91360aa8aa2108c6e304316a6d13ca

        model.addAttribute("selectedCategorieId", article.getCategorieArticle().getId_categorie());
        model.addAttribute("article", article);
        return "details_vente";
    }

    @PostMapping("/encheres/encherir")
    public String encherir(){
       // code de raman pour la fonction faire une enchere
        return "redirect:/encheres/details";
    }


<<<<<<< HEAD
=======

// page nouvelle vente validation de l'article creer
    @PostMapping("/encheres/create")
    public String createEnchere(@Valid @ModelAttribute Article article, BindingResult result,  Model model){


        if (result.hasErrors()) {
            System.out.println(" ERREURS VALIDATION :");
            result.getAllErrors().forEach(error -> System.out.println("  - " + error.getDefaultMessage()));

        model.addAttribute("categorieList", categorieService.readAll());
        model.addAttribute("article", article);
        return "add_enchere";
        }
        //recuperation des info du vendeur
        Utilisateur vendeurConnecte = utilisateurService.recuperationIdUtilisateurActif();
        article.setVendeur(vendeurConnecte);

        //traite erreur categorie
        if(article.getCategorieArticle() == null || article.getCategorieArticle().getId_categorie() == 0) {
            article.setCategorieArticle(categorieService.readAll().get(0));
        }

        // RETRAIT = NULL (ignore colonne id_retrait)
        article.setLieuxRetrait(null);

        // ACHETEUR = NULL (normal pour nouvelle enchère)
        article.setAcheteur(null);



        try {
            articleService.create(article);
            return "redirect:/encheres";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("categorieList", categorieService.readAll());
            model.addAttribute("article", article);
            model.addAttribute("error", "Erreur lors de la sauvegarde");
            return "add_enchere";
        }
    }


    @PostMapping("/encheres/filtres")
    public String filtrerArticles(Model model, @RequestParam("search") String search, @RequestParam(value = "categorie", required = false, defaultValue = "0") long id){
            List<Article> articleList = articleService.readAllArticlesEnVenteFiltre(search, id);
            List<Categorie> categorieList = categorieService.readAll();
            model.addAttribute("articleList", articleList);
        model.addAttribute("categorieList", categorieList);
        model.addAttribute("id_categorie_selectionnee", id); //affichage avec la catégorie sélectionnée pour une meilleure UX
        model.addAttribute("search", search); //affichage du mot précédemment recherché pour une meilleure UX
            return "encheres";
    }
>>>>>>> 516bc663ef91360aa8aa2108c6e304316a6d13ca
}
