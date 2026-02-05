package fr.eni.enchere.service;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.repository.ArticleRepositorySQL;
import fr.eni.enchere.repository.EnchereRepository;
import fr.eni.enchere.repository.UtilisateurRepository;
import fr.eni.enchere.repository.UtilisateurRepositorySql;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnchereServiceImpl implements EnchereService{
    private final ArticleRepositorySQL articleRepositorySQL;
    private final ArticleService articleService;
    private final UtilisateurService utilisateurService;
    //private final UtilisateurRepositorySql utilisateurRepositorySql;
    EnchereRepository enchereRepository;
    private final UtilisateurRepository utilisateurRepository;

    public EnchereServiceImpl(EnchereRepository enchereRepository, ArticleRepositorySQL articleRepositorySQL, ArticleService articleService, UtilisateurService utilisateurService, UtilisateurRepositorySql utilisateurRepositorySql, UtilisateurRepository utilisateurRepository) {
        this.enchereRepository = enchereRepository;
        this.articleRepositorySQL = articleRepositorySQL;
        this.articleService = articleService;
        this.utilisateurService = utilisateurService;
        //this.utilisateurRepositorySql = utilisateurRepositorySql;
        this.utilisateurRepository = utilisateurRepository;
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

    ///////////// Logique d'une enchere (Raman)


    @Override
    public void placerEnchere(long idArticle, long idUtilisateur, int montantPropose) throws Exception {


        Article article = articleService.readById(idArticle);

        // Vérifier si l'article existe
        if (article == null) {
            throw new Exception("Article introuvable.");
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
            throw new Exception("Le montant proposé doit être supérieur au prix actuel.");
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

            // REMBOURSE les points: ancien prêt + montant
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
        article.setAcheteur(encherisseur); // le nouvel acheteur est l'utilisateur actif
        articleService.update(article); // Met à jour la base de données


        //// Création d'une nouvelle enchère
        Enchere nouvelleEnchere = new Enchere();
        nouvelleEnchere.setDate_enchere(LocalDate.now()); // Date du jour
        nouvelleEnchere.setMontant_enchere(montantPropose); // Montant de l'enchère
        nouvelleEnchere.setEncherisseur(encherisseur); // enchérisseur
        nouvelleEnchere.setArticles(article); // article
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

    @Override
    public List<Utilisateur> afficherListeEncherisseurs(long id){
        List<Long> listeidEncherisseurs = enchereRepository.readAllidUtilisateurByIdArticle(id);
        List<Utilisateur> listeEncherisseurs = listeidEncherisseurs.stream()
                .map(i -> utilisateurRepository.readById(i))
                .collect(Collectors.toList());
        return listeEncherisseurs;
    }

    @Override
    public int getBestEnchereByUtilisateurByArticle(long id_encherisseur, long id_article){
        List<Long> enchereIdList = enchereRepository.readAllByIdUtilisateurAndByIdArticle(id_encherisseur, id_article);
        if(enchereIdList.isEmpty()){
            return 0;
        } else{
            return enchereIdList.get(0).intValue();
        }

    }


}
