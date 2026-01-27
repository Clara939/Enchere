package fr.eni.enchere.service;

import fr.eni.enchere.bo.Utilisateur;

import java.util.List;

public interface UtilisateurService {

    void createUtilisateur(Utilisateur utilisateur);

    List<Utilisateur> readAll();

    Utilisateur readById(long id);

    void updateUtilisateur(Utilisateur utilisateur);


    void deleteUtilisateur(long id);

    //Cette fonction permet de récupérer l'utilisateur connecté afin d'afficher plus tard les informations le concernant
    Utilisateur recuperationIdUtilisateurActif();
}
