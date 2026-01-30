package fr.eni.enchere.controller.converter;


import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.service.*;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        //utilisateur connecté
        Utilisateur utilisateurConnecte = utilisateurService.recuperationIdUtilisateurActif();
        //nouvel article
        Article article = new Article();
        article.setId_article(0);

        //pré-remplit retrait avec infos utilisateur connecté
        if (utilisateurConnecte != null){
            Retrait retraitVendeur = new Retrait();
            retraitVendeur.setRue(utilisateurConnecte.getRue());
            retraitVendeur.setCode_postal(utilisateurConnecte.getCode_postal());
            retraitVendeur.setVille(utilisateurConnecte.getVille());
            article.setLieuxRetrait(retraitVendeur);
            article.setVendeur(utilisateurConnecte);
        }

        model.addAttribute("article", article);
        //liste catégories
        model.addAttribute("categorieList", categorieService.readAll());
        //pour afficher info de l'utilisateur connecte
        model.addAttribute("utilisateurConnecte", utilisateurConnecte);

        return "add_enchere";
    }

    //page modification de l'article
    @GetMapping("/encheres/update")
    public String modifierArticle(@RequestParam("id")long id_article, Model model){
        //recupere l'article existant
        Article article = articleService.readById(id_article);
        //verifie l'etat= creer
        if(!"CREER".equals(article.getEtat_vente())){
            return "redirect:/encheres";
        }
        //pré-remplis le formulaire
        model.addAttribute("article", article);
        model.addAttribute("categorieList", categorieService.readAll());
        model.addAttribute("utilisateurConnecte", utilisateurService.recuperationIdUtilisateurActif());

        return "add_enchere";
    }


// page nouvelle vente validation de l'article creer et page modification de l'article
// REMPLACEZ les 2 méthodes POST par 1 SEULE
@PostMapping("/encheres/save")
public String saveArticle(@RequestParam("categorieId") long categorieId,@Valid @ModelAttribute Article article, BindingResult result,  Model model){

    if (result.hasErrors()) {
        model.addAttribute("categorieList", categorieService.readAll());
        model.addAttribute("article", article);
        return "add_enchere";
    }

    // categorie
    Categorie categorieChoisie = categorieService.readById(categorieId);
    article.setCategorieArticle(categorieChoisie);
    //vendeur
    Utilisateur vendeurConnecte = utilisateurService.recuperationIdUtilisateurActif();
    article.setVendeur(vendeurConnecte);
    //retrait
    Retrait retrait = new Retrait();
    retrait.setRue(vendeurConnecte.getRue());
    retrait.setCode_postal(vendeurConnecte.getCode_postal());
    retrait.setVille(vendeurConnecte.getVille());
    retraitService.createRetrait(retrait);
    article.setLieuxRetrait(retrait);
    article.setAcheteur(null);

    try {
        if(article.getId_article() == 0L) {
            articleService.create(article);  // CREATE
        } else {
            articleService.update(article);  // UPDATE
        }
        return "redirect:/encheres";
    } catch (Exception e) {
        e.printStackTrace();
        model.addAttribute("categorieList", categorieService.readAll());
        return "add_enchere";
    }
}



//    @GetMapping("/encheres/filtres")
//    public String

    @PostMapping("/encheres/filtres")
    public String filtrerArticles(Model model, @RequestParam("search") String search, @RequestParam(value = "categorie", required = false, defaultValue = "0") long id){
            List<Article> articleList = articleService.readAllArticlesEnVenteFiltre(search, id);
            List<Categorie> categorieList = categorieService.readAll();
            model.addAttribute("articleList", articleList);
        model.addAttribute("categorieList", categorieList);
            return "encheres";
    }

    //permet la supression de l'article
    @GetMapping("/encheres/delete")
    public String deleteArticle(@RequestParam("id") long id_article){
        Article article = articleService.readById(id_article);
        //verifie si bien etat=CREER
        if (!"CREER".equals(article.getEtat_vente())){
            return "redirect:/encheres?error=enchereDemarree";
        }
        //verifie si proprietaire uniquement
        Utilisateur vendeurConnecte = utilisateurService.recuperationIdUtilisateurActif();
        if (article.getVendeur().getId_utilisateur() != vendeurConnecte.getId_utilisateur()){
            return "redirect:/encheres?error=nonAutorise";
        }
        //suppression de l'article
        articleService.delete(id_article);
        return "redirect:/encheres?success=supprime";
    }
}
