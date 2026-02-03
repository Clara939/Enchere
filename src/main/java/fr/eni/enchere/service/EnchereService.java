package fr.eni.enchere.service;

import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.bo.Utilisateur;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

public interface EnchereService {
    void createEnchere(Enchere enchere);

    List<Enchere> readAll();

    Enchere readById(long id);

    void updateEnchere(Enchere enchere);

    void deleteEnchere(long id);

//  Méthode pour faire une enchère
    void placerEnchere(long idArticle, long idUtilisateur, int montantPropose) throws Exception;

//    remporter une enchere
     String getPageRemportee(Long idArticle, Utilisateur utilisateurConnecte, Model model);


    boolean afficherBoutonEnchereEnCours(LocalDate date_debut, LocalDate date_fin);
}
