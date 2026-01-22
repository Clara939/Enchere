package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Utilisateur;

import java.util.List;

public interface UtilisateurRepository {
    void createUtilisateur(Utilisateur utilisateur);

    List<Utilisateur> readAll();

    Utilisateur readById(long id);

    void updateUtilisateur(Utilisateur utilisateur);

    void deleteUtilisateur(long id);
}
