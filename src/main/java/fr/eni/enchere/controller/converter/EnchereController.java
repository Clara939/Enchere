package fr.eni.enchere.controller.converter;


import fr.eni.enchere.bo.*;
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
        //récupération de l'idUtilisateur pour affichage sélectif des boutons
        Utilisateur utilisateurConnecte = null;
        try {
            utilisateurConnecte = utilisateurService.recuperationIdUtilisateurActif();
        } catch (Exception e) {
            // on laisse null si erreur (aucun utilisateur connecté)
        }
         List<Article> articleList = articleService.readAllArticlesEnVente();
        List<Categorie> categorieList = categorieService.readAll();
        model.addAttribute("utilisateurConnecte", utilisateurConnecte);
model.addAttribute("articleList", articleList);
model.addAttribute("categorieList", categorieList);
        model.addAttribute("id_categorie_selectionnee", 0);
        model.addAttribute("search", "");
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
        //verifie l'etat= cree
        if(!"CREE".equalsIgnoreCase(article.getEtat_vente())){
            return "redirect:/encheres";
        }
        //pré-remplis le formulaire
        model.addAttribute("article", article);
        model.addAttribute("categorieList", categorieService.readAll());
        model.addAttribute("utilisateurConnecte", utilisateurService.recuperationIdUtilisateurActif());

        return "add_enchere";
    }


// page nouvelle vente validation de l'article créé et page modification de l'article
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

    @PostMapping("/encheres/filtres")
    public String filtrerArticles(Model model, @RequestParam(value = "search", defaultValue = "") String search,
                                  @RequestParam(value = "categorie", required = false, defaultValue = "0") long id,
                                  //@RequestParam(value = "achat", required = false, defaultValue = "false") boolean achat,
                                  @RequestParam(value = "encheres_radio", required = false) String radioSelectionnee,  // "achat" ou "vente"
                                  @RequestParam(value = "encheres_ouvertes", required = false, defaultValue = "false") boolean encheres_ouvertes,
                                  @RequestParam(value = "mes_encheres_cours", required = false, defaultValue = "false") boolean mes_encheres_cours,
                                  @RequestParam(value = "mes_encheres_remportees", required = false, defaultValue = "false") boolean mes_encheres_remportees,
                                  //@RequestParam(value = "vente", required = false, defaultValue = "false") boolean vente,
                                  @RequestParam(value = "mes_ventes_cours", required = false, defaultValue = "false") boolean mes_ventes_cours,
                                  @RequestParam(value = "ventes_non_debutees", required = false, defaultValue = "false") boolean ventes_non_debutees,
                                  @RequestParam(value = "ventes_terminees", required = false, defaultValue = "false") boolean ventes_terminees
    ){
            List<Article> articleList = articleService.readAllArticlesEnVenteFiltre(search, id, radioSelectionnee, encheres_ouvertes, mes_encheres_cours, mes_encheres_remportees, mes_ventes_cours, ventes_non_debutees, ventes_terminees);
            List<Categorie> categorieList = categorieService.readAll();
        boolean achat = "achat".equals(radioSelectionnee);
        boolean vente = "vente".equals(radioSelectionnee);
        model.addAttribute("articleList", articleList);
        model.addAttribute("categorieList", categorieList);
        model.addAttribute("id_categorie_selectionnee", id);
        model.addAttribute("search", search);
        model.addAttribute("achat", achat);
        model.addAttribute("encheres_ouvertes", encheres_ouvertes);
        model.addAttribute("mes_encheres_cours", mes_encheres_cours);
        model.addAttribute("mes_encheres_remportees", mes_encheres_remportees);
        model.addAttribute("vente", vente);
        model.addAttribute("mes_ventes_cours", mes_ventes_cours);
        model.addAttribute("ventes_non_debutees", ventes_non_debutees);
        model.addAttribute("ventes_terminees", ventes_terminees);

            return "encheres";
    }

    //permet la supression de l'article
    @GetMapping("/encheres/delete")
    public String deleteArticle(@RequestParam("id") long id_article){
        Article article = articleService.readById(id_article);
        //verifie si bien etat=CREE
        if (!"CREE".equalsIgnoreCase(article.getEtat_vente())){
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
