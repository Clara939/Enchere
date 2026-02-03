package fr.eni.enchere.controller.converter;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.service.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VenteController {
    UtilisateurService  utilisateurService;
    ArticleService articleService;
    CategorieService categorieService;
    EnchereService enchereService;
    RetraitService retraitService;

    public VenteController(UtilisateurService utilisateurService, ArticleService articleService, RetraitService retraitService, EnchereService enchereService, CategorieService categorieService) {
        this.utilisateurService = utilisateurService;
        this.articleService = articleService;
        this.retraitService = retraitService;
        this.enchereService = enchereService;
        this.categorieService = categorieService;
    }

    //page faire une enchere
    @GetMapping("/encheres/details")
    public String afficherDetail(@RequestParam(name = "id_article")long id, Model model){
        Article article = articleService.readById(id);
        model.addAttribute("article", article);

        return "details_vente";
    }
    //permet d'encherir sur un article
    @GetMapping("/encherir")
    public String afficherPageEncherir(
            @RequestParam("id") long idArticle,
            Model model) {

        // Récupérer l'article par son ID
        Article article = articleService.readById(idArticle); // Récupérer l'article par son ID

        // Obtenir l'utilisateur autorisé, renvoie l'objet Utilisateur (l'utilisateur actuellement connecté)
        Utilisateur utilisateurConnecte = utilisateurService.recuperationIdUtilisateurActif();

        // déterminons le prix actuel
        int prixActuel = article.getPrix_vente(); // prix de vente actuel

        // Si aucune enchère, le prix actuel est le prix initial
        if (prixActuel == 0) {
            prixActuel = article.getPrix_initial();
        }

        // Enchère minimale = prix actuel + 1
        int enchereMinimale = prixActuel + 1;

        // Transmission des données au modèle HTML
        model.addAttribute("article", article);
        //à gérer
        model.addAttribute("monObjet", utilisateurConnecte.getId_utilisateur() ==  article.getVendeur().getId_utilisateur());
        ///
        model.addAttribute("enchereMinimale", enchereMinimale);
        model.addAttribute("prixActuel", prixActuel);

        return "details_vente";

    }

    @PostMapping("/encheres/placer")
    public String placerEnchere(
            @RequestParam("id_article") long idArticle,
            @RequestParam("montantPropose") int montantPropose,
            Model model
    ) {

        // Obtenir l'utilisateur autorisé, renvoie l'objet Utilisateur (l'utilisateur actuellement connecté)
        Utilisateur utilisateurConnecte = utilisateurService.recuperationIdUtilisateurActif();

        if (utilisateurConnecte == null) {
            return "redirect:/login"; // Rediriger vers la page de connexion si l'utilisateur n'est pas connecté
        }

        // Service (place enchère)
        try {
            enchereService.placerEnchere(idArticle, utilisateurConnecte.getId_utilisateur(), montantPropose);

            model.addAttribute("success", "Enchere placee avec succes");
            return "redirect:/encheres";
            // il faut changer tout les execptions
        } catch (Exception e) {
            Article article = articleService.readById(idArticle);

            int prixActuel = article.getPrix_vente();
            if (prixActuel == 0) {
                prixActuel = article.getPrix_initial();
            }
            int enchereMinimale = prixActuel + 1;

            // Transmission des données au modèle
            model.addAttribute("article", article);
            model.addAttribute("utilisateurConnecte", utilisateurConnecte);
            model.addAttribute("prixActuel", prixActuel);
            model.addAttribute("enchereMinimale", enchereMinimale);
            model.addAttribute("error", e.getMessage()); // Afficher le message d'erreur


            return "details_vente";
        }

    }


    //quand on remporte une enchere
    @GetMapping("/encheres/fini")
    public String enchereGagner(@RequestParam("id") Long id_article, Model model) {
        Utilisateur utilisateurConnecte = utilisateurService.recuperationIdUtilisateurActif();
        return enchereService.getPageRemportee(id_article, utilisateurConnecte, model);
    }

}
