package fr.eni.enchere.service;

import fr.eni.enchere.bo.Utilisateur;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface UtilisateurService {

    void createUtilisateur(Utilisateur utilisateur);

    List<Utilisateur> readAll();

    Utilisateur readById(long id);

    void updateUtilisateur(Utilisateur utilisateur);

    void deleteUtilisateur(long id);

    //Cette fonction permet de récupérer l'utilisateur connecté afin d'afficher plus tard les informations le concernant
    Utilisateur recuperationIdUtilisateurActif();

//    cette fonction permet de rendre un compte inactif
    void desactiverCompte(long id);

    //Cette fonction permet de vérifier qu'un pseudo n'est pas déjà dans la table Utilisateurs
    //elle retourne false si le pseudo est déjà présent, true sinon
    boolean verificationDoublonPseudo(String pseudo);

    boolean verificationDoublonEmail(String email);

    void verificationDoublons(Utilisateur utilisateur, BindingResult bindingResult, String confirmationMDP);
}
