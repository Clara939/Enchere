package fr.eni.enchere.controller.converter;


import fr.eni.enchere.bo.*;
import fr.eni.enchere.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class EnchereController {
    UtilisateurService  utilisateurService;
    ArticleService articleService;
    CategorieService categorieService;
    EnchereService enchereService;
    RetraitService retraitService;
    PhotoService photoService;

    public EnchereController(UtilisateurService utilisateurService, ArticleService articleService, CategorieService categorieService, EnchereService enchereService, RetraitService retraitService, PhotoService photoService) {
        this.utilisateurService = utilisateurService;
        this.articleService = articleService;
        this.categorieService = categorieService;
        this.enchereService = enchereService;
        this.retraitService = retraitService;
        this.photoService = photoService;
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

        if (model.containsAttribute("successMessage")) {
            model.addAttribute("successMessage", model.asMap().get("successMessage"));
        }

        if (model.containsAttribute("errorMessage")) {
            model.addAttribute("errorMessage", model.asMap().get("errorMessage"));
        }

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
    // page nouvelle vente validation de l'article créé
    @PostMapping("/encheres/save")
    public String saveArticle(@RequestParam("categorieId") long categorieId, @RequestParam(value = "photoArticle", required = false) MultipartFile photoArticle, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {

        System.out.println(" POST save - categorieId=" + categorieId);

        try {
            // Créer Article MANUELLEMENT
            Article article = new Article();
            article.setNom_article(request.getParameter("nom_article"));
            article.setDescription(request.getParameter("description"));
            article.setPrix_initial(Integer.parseInt(request.getParameter("prix_initial")));
            article.setDate_debut_encheres(LocalDate.parse(request.getParameter("date_debut_encheres")));
            article.setDate_fin_encheres(LocalDate.parse(request.getParameter("date_fin_encheres")));
            article.setEtat_vente("CREE");

            //  RETRAIT depuis formulaire
            Retrait retrait = new Retrait();
            retrait.setRue(request.getParameter("lieuxRetrait.rue"));
            retrait.setCode_postal(request.getParameter("lieuxRetrait.code_postal"));
            retrait.setVille(request.getParameter("lieuxRetrait.ville"));
            retrait.setId_retrait(0L);
            article.setLieuxRetrait(retrait);

            // VENDEUR connecté
            Utilisateur vendeur = utilisateurService.recuperationIdUtilisateurActif();
            article.setVendeur(vendeur);

            System.out.println(" CRÉATION: " + article.getNom_article() + " à " + retrait.getRue());

            // Service
            articleService.creerArticleComplet(article, categorieId, photoArticle);

            // SUCCÈS avec flash message
            redirectAttributes.addFlashAttribute("successMessage", "Article créé avec succès !");
            return "redirect:/encheres";

        } catch (Exception e) {
            System.err.println(" ERREUR SAVE: " + e.getMessage());
            e.printStackTrace();

            // Erreur → Retour formulaire + article + messages
            Article article = new Article();
            model.addAttribute("article", article);
            model.addAttribute("categorieList", categorieService.readAll());
            model.addAttribute("utilisateurConnecte", utilisateurService.recuperationIdUtilisateurActif());
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur création: " + e.getMessage());
            return "redirect:/encheres/add";
        }
    }
    //page modification de l'article
    @GetMapping("/encheres/update")
    public String modifierArticle(@RequestParam("id")long id_article, Model model){

        if (model.containsAttribute("successMessage")) {
            model.addAttribute("successMessage", model.asMap().get("successMessage"));
        }

        if (model.containsAttribute("errorMessage")) {
            model.addAttribute("errorMessage", model.asMap().get("errorMessage"));
        }

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

        return "update_article";
    }
    @PostMapping("/encheres/update")
    public String updateArticle(@RequestParam("categorieId") long categorieId, @RequestParam(value = "photoArticle", required = false) MultipartFile photoArticle, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model){
        System.out.println("POST update - categorieId=" + categorieId);

        try {
//            recuperer article existant par son id
            long idArticle = Long.parseLong(request.getParameter("id_article"));
//            verifier que l'article existe et est modifiable (etat "CREE")
            Article article = articleService.readById(idArticle);
            if (article == null || !"CREE".equalsIgnoreCase(article.getEtat_vente())){
                redirectAttributes.addFlashAttribute("errorMessage", "Article introuvable ou non modifiable.");
                return "redirect:/encheres";
            }
//            verifier que c'est bien le proprietaire
            Utilisateur vendeurConnecte = utilisateurService.recuperationIdUtilisateurActif();
            if (article.getVendeur().getId_utilisateur() != vendeurConnecte.getId_utilisateur()){
                redirectAttributes.addFlashAttribute("errorMessage", "Non autorisé à modifier cet article.");
                return "redirect:/encheres";
            }
//            recuperer et mettre à jour les données du formulaire
            article.setNom_article(request.getParameter("nom_article"));
            article.setDescription(request.getParameter("description"));

            article.setPrix_initial(Integer.parseInt(request.getParameter("prix_initial")));
            article.setDate_debut_encheres(LocalDate.parse(request.getParameter("date_debut_encheres")));
            article.setDate_fin_encheres(LocalDate.parse(request.getParameter("date_fin_encheres")));

//            mise à jour du retrait
            Retrait retrait = article.getLieuxRetrait();
            if (retrait == null){
                retrait = new Retrait();
                article.setLieuxRetrait(retrait);
            }
            retrait.setRue(request.getParameter("lieuxRetrait.rue"));
            retrait.setCode_postal(request.getParameter("lieuxRetrait.code_postal"));
            retrait.setVille(request.getParameter("lieuxRetrait.ville"));

            System.out.println("MODIFICATION: " + article.getNom_article() + " à " + retrait.getRue());

//            mise à jour via le service
            articleService.modifierArticleComplet(article, categorieId, photoArticle);

//            succes avec flash message
            redirectAttributes.addFlashAttribute("successMessage", "Article modifié avec succès !");
            return "redirect:/encheres";
        }
        catch (Exception e) {
            System.err.println("ERREUR UPDATE: " + e.getMessage());
            e.printStackTrace();

            // En cas d'erreur : retour au formulaire de modification avec données pré-remplies
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur modification: " + e.getMessage());
            return "redirect:/encheres/update?id=" + request.getParameter("id_article");
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
