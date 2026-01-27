package fr.eni.enchere.service;

import fr.eni.enchere.bo.Role;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.repository.RoleRepository;
import fr.eni.enchere.repository.UtilisateurRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

//import javax.management.relation.Role;
import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService{
    UtilisateurRepository utilisateurRepository;
    RoleRepository roleRepository;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, RoleRepository roleRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void createUtilisateur(Utilisateur utilisateur) {
        this.utilisateurRepository.createUtilisateur(utilisateur);
    //on ajoute un role au nouvel utilisateur pour qu'il puisse se connecter (utilisateur par défaut)
        roleRepository.createRole(new Role(utilisateur.getPseudo(), "ROLE_UTILISATEUR"));
    }

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

    @Override
    public void desactiverCompte(long id) {
        Utilisateur utilisateur = utilisateurRepository.readById(id);
        if (utilisateur != null){
            utilisateur.setActif(false);
            utilisateurRepository.updateUtilisateur(utilisateur);
        }


    }


    //Cette fonction permet de vérifier qu'un pseudo n'est pas déjà dans la table Utilisateurs
    //elle retourne false si le pseudo est déjà présent, true sinon
    @Override
    public boolean verificationDoublonPseudo(String pseudo){
        List<String> pseudoListe = utilisateurRepository.readAllPseudo();
        for (String pseudoExistant : pseudoListe){
            if(pseudoExistant.equals(pseudo)){
                return false;
            }
        }
        return true;
    }

    //Cette fonction permet de vérifier qu'un mail n'est pas déjà dans la table Utilisateurs
    //elle retourne false si le mail est déjà présent, true sinon
    @Override
    public boolean verificationDoublonEmail(String email){
        List<String> emailListe = utilisateurRepository.readAllEmail();
        for (String mailExistant : emailListe){
            if(mailExistant.equals(email)){
                return false;
            }
        }
        return true;
    }

    //Fonction permettant de nommer les erreurs, leur assigner un message et les injecter dans le BindingResult
    @Override
    public void verificationDoublons(Utilisateur utilisateur, BindingResult bindingResult, String confirmationMDP) {
        // Vérification pseudo
        if (!verificationDoublonPseudo(utilisateur.getPseudo())) {
            bindingResult.rejectValue("pseudo", "error.pseudo.doublon",
                    "Ce pseudo est déjà pris");
        }

        // Vérification email
        if (!verificationDoublonEmail(utilisateur.getEmail())) {
            bindingResult.rejectValue("email", "error.email.doublon",
                    "Cet email est déjà utilisé");
        }

        //fonction permettant de vérifier que les deux mots de passe lors de l'inscription sont identiques
        if (!utilisateur.getMot_de_passe().equals(confirmationMDP)) {
            bindingResult.rejectValue("mot_de_passe", "error.motdepasse.confirmation",
                    "Les mots de passe ne sont pas identiques");
            return;
        }
    }



}
