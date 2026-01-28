package fr.eni.enchere.controller.converter;


import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EnchereController {
    UtilisateurService  utilisateurService;
    ArticleService articleService;
    CategorieService categorieService;
    EnchereService enchereService;
    RetraitService retraitService;

    public EnchereController(UtilisateurService utilisateurService, ArticleService articleService, RetraitService retraitService, EnchereService enchereService, CategorieService categorieService) {
        this.utilisateurService = utilisateurService;
        this.articleService = articleService;
        this.retraitService = retraitService;
        this.enchereService = enchereService;
        this.categorieService = categorieService;
    }

    @GetMapping("/encheres")
    public String afficherEncheres(Model model){
        List<Article> articleList = articleService.readAllArticlesEnVente();
model.addAttribute("articleList", articleList);
        return "encheres";
    }

// page nouvelle vente ( création de l'article a mettre en ventes)
    @GetMapping("/encheres/add")
    public String nouvelleVente(Model model){
        //nouvel article
        model.addAttribute("article", new Article());
        //liste catégories
        model.addAttribute("categorieList", categorieService.readAll());
        return "add_enchere";
    }
    @PostMapping("/encheres/create")
    public String createEnchere(@ModelAttribute Article article, Model model){
        articleService.create(article);
        return "redirect:/encheres";
    }

//    @PostMapping("/encheres/filtres")
//    public String filtrerArticles(@ModelAttribute Article article, Model model){
//return "/encheres";
//    }
}
