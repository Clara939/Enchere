package fr.eni.enchere.service;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.repository.UtilisateurRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService{
    UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public void createUtilisateur(Utilisateur utilisateur) { this.utilisateurRepository.createUtilisateur(utilisateur); }

    @Override
    public List<Utilisateur> readAll() {return this.utilisateurRepository.readAll(); }

    @Override
    public Utilisateur readById(long id) { return this.utilisateurRepository.readById(id); }

    @Override
    public void updateUtilisateur(Utilisateur utilisateur) { this.utilisateurRepository.updateUtilisateur(utilisateur); }

    @Override
    public void deleteUtilisateur(long id) { this.utilisateurRepository.deleteUtilisateur(id); }

    //Cette fonction permet de récupérer l'utilisateur connecté afin d'afficher plus tard les informations le concernant
    @Override
    public Utilisateur recuperationIdUtilisateurActif(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String pseudo = auth.getName();
            Utilisateur utilisateurConnecte = utilisateurRepository.readByPseudo(pseudo);
            return utilisateurConnecte;
    }
}
