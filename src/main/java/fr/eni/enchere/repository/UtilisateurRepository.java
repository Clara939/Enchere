package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Utilisateur;

import java.util.List;

public interface UtilisateurRepository {
    void createUtilisateur(Utilisateur utilisateur);

    List<Utilisateur> readAll();

    Utilisateur readById(long id);

    void updateUtilisateur(Utilisateur utilisateur);

    void deleteUtilisateur(long id);

    Utilisateur readByPseudo(String pseudo);

    //fonction permettant de récupérer tous les pseudos des utilisateurs de la base
    List<String> readAllPseudo();

    //fonction permettant de récupérer tous les mails des utilisateurs de la base
    List<String> readAllEmail();
}
