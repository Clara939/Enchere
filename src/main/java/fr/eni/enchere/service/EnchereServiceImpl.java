package fr.eni.enchere.service;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.repository.ArticleRepositorySQL;
import fr.eni.enchere.repository.EnchereRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService{
    private final ArticleRepositorySQL articleRepositorySQL;
    private final ArticleService articleService;
    private final UtilisateurService utilisateurService;
    EnchereRepository enchereRepository;

    public EnchereServiceImpl(EnchereRepository enchereRepository, ArticleRepositorySQL articleRepositorySQL, ArticleService articleService, UtilisateurService utilisateurService) {
        this.enchereRepository = enchereRepository;
        this.articleRepositorySQL = articleRepositorySQL;
        this.articleService = articleService;
        this.utilisateurService = utilisateurService;
    }

    @Override
    public void createEnchere(Enchere enchere) { this.enchereRepository.createEnchere(enchere); }

    @Override
    public List<Enchere> readAll() { return this.enchereRepository.readAll(); }

    @Override
    public Enchere readById(long id) { return this.enchereRepository.readById(id); }

    @Override
    public void updateEnchere(Enchere enchere) { this.enchereRepository.updateEnchere(enchere); }

    @Override
    public void deleteEnchere(long id) { this.enchereRepository.deleteEnchere(id); }

    ///////////// Logique d'un enchere (Raman)
    @Override
    public void placerEnchere(long idArticle, long idUtilisateur, int montantPropose) throws Exception {
        Article article = articleService.readById(idArticle);

        // Vérifier si l'article existe
        if (article == null) {
            throw new Exception("Article introvable.");
        }

        //// Récupération d'un utilisateur à partir de la base de données
        Utilisateur encherisseur = utilisateurService.readById(idUtilisateur);

        // Vérifier si l'utilisateur existe
        if (encherisseur == null) {
            throw new Exception("Utilisateur introuvable.");
        }

        //// Nous déterminons le prix actuel
        int prixActuel = article.getPrix_vente();

        // Si aucune enchère, le prix actuel est le prix initial
        if (prixActuel == 0) {
            prixActuel = article.getPrix_initial();
        }

        //// Vérifier si le montant proposé est supérieur au prix actuel
        if (montantPropose <= prixActuel) {
            throw new Exception("Le montrant propose doit etre superieur au prix actuel.");
        }

        //// VÉRIFICATION - L'utilisateur dispose-t-il de suffisamment de points
        if (encherisseur.getCredit() < montantPropose) {
            throw new Exception("Crédit insuffisant pour effectuer cette enchère.");
        }

        //// Trouvez la meilleure offre précédente
        Enchere ancienneEnchere = enchereRepository.findTopEnchereByArticle(idArticle);

        //// Nous rendons les points au vainqueur précédent
        if (ancienneEnchere != null) {
            Utilisateur ancienEncherisseur = ancienneEnchere.getEncherisseur();

            // Le montant a son enchere
            int montantRembourser = ancienneEnchere.getMontant_enchere();

            // REMBOURS les points: ancien prêt + montant
            ancienEncherisseur.setCredit(ancienEncherisseur.getCredit() + montantRembourser);

            // Met à jour la base de données
            utilisateurService.updateUtilisateur(ancienEncherisseur);
        }

        //// Annulons les points d'un nouveau participant
        encherisseur.setCredit(encherisseur.getCredit() - montantPropose);
        // Met à jour la base de données
        utilisateurService.updateUtilisateur(encherisseur);


        //// Mise à jour du prix de vente de article
        article.setPrix_vente(montantPropose); // Nouveau prix de vente = montant proposé
        article.setAcheteur(encherisseur); // Nous avons defini acheteur comme participant actuel
        articleService.update(article); // Met a jour la base de données


        //// Création d'une nouvelle enchère
        Enchere nouvelleEnchere = new Enchere();

        nouvelleEnchere.setDate_enchere(LocalDate.now()); // Date actuelle
        nouvelleEnchere.setMontant_enchere(montantPropose); // Montant sum
        nouvelleEnchere.setEncherisseur(encherisseur); // Contacter l'enchanteur
        nouvelleEnchere.setArticles(article); // Contacter l'article
        enchereRepository.createEnchere(nouvelleEnchere); // Enregistrer dans la base de données
    }

    public String getPageRemportee(Long idArticle, Utilisateur utilisateurConnecte, Model model){
        Article article = articleService.readById(idArticle);

        if (!peutAccederPageRemportee(article, utilisateurConnecte)){
            return "redirect:/encheres";
        }
        model.addAttribute("article", article);

        if (utilisateurConnecte.equals(article.getVendeur())){
            return "enchere_remporter_vendeur";
        }
        return "enchere_remporter_acheteur";
    }

    private boolean peutAccederPageRemportee(Article article, Utilisateur utilisateur){
// verification sur l'article
        if (article == null || !"VENDU".equals(article.getEtat_vente())){
            return false;
        }
        if (utilisateur == null){
            return false;
        }

//        verification sur le vendeur
        if (utilisateur.equals(article.getVendeur())){
            return article.getPrix_vente() != 0 && article.getAcheteur() != null;
        }

//        verification acheteur doit etre le gagnant
        return utilisateur.equals(article.getAcheteur());
    }

    @Override
    public boolean afficherBoutonEnchereEnCours(LocalDate date_debut, LocalDate date_fin){
        if(date_debut.isBefore(LocalDate.now()) & date_fin.isAfter(LocalDate.now())){
            return true;
        } else{
            return false;
        }
}
}
