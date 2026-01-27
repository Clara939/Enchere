package fr.eni.enchere.controller.converter;


import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

//    @GetMapping("/encheres/add")
//    public String nouvelleVente(Model model){
//        List<Categorie>categorieList = CategorieService.g
//
//    }

}
