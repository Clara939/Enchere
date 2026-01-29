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
    @PostMapping("/encheres/encherir")
    public String faireUneEnchere(){
    // code de raman
        return "redirect:encheres";
    }
}
