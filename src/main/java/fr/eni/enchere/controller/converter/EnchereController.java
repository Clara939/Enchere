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
public class EnchereController {
    UtilisateurService utilisateurService;
    ArticleService articleService;
    CategorieService categorieService;
    RetraitService retraitService;
    EnchereService enchereService;

    public EnchereController(UtilisateurService utilisateurService, ArticleService articleService, CategorieService categorieService, RetraitService retraitService, EnchereService enchereService) {
        this.utilisateurService = utilisateurService;
        this.articleService = articleService;
        this.categorieService = categorieService;
        this.retraitService = retraitService;
        this.enchereService = enchereService;
    }

    @GetMapping("/encheres/details")
    public String afficherDetail(@RequestParam(name = "id") long id, Model model){
        Article article = articleService.readById(id);

        model.addAttribute("selectedCategorieId", article.getCategorieArticle().getId_categorie());
        model.addAttribute("article", article);
        return "details_vente";
    }

    @PostMapping("/encheres/encherir")
    public String encherir(){
       // code de raman pour la fonction faire une enchere
        return "redirect:/encheres/details";
    }


}
