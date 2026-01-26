package fr.eni.enchere.controller.converter;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UtilisateurController {
    private UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

//    page d'inscription le get et le post, le post renvoie sur la page d'accueil si inscription reussi et si annulation de l'inscription renvoie sur page accueil aussi.
    @GetMapping("/inscription")
    public String inscrire(Model model){
        List<Utilisateur>utilisateurList = utilisateurService.readAll();

        model.addAttribute("utilisateur", new Utilisateur());

        return "inscription";
    }

    @PostMapping("/inscription")
    public String inscrireUtilisateur(@Valid @ModelAttribute(name = "utilisateur") Utilisateur utilisateur, BindingResult bindingResult){
       if (bindingResult.hasErrors()){
           return "redirect:/";
       }


        utilisateurService.createUtilisateur(utilisateur);

        return "redirect:/";
    }


// page afficher profil d'une personne
    @GetMapping("/profil")
    public String profil(@RequestParam(name = "id") long id, Model model){
        Utilisateur utilisateur = utilisateurService.readById(id);

        model.addAttribute("utilisateur", utilisateur);

        return "afficher_un_profil";
    }


//    page afficher Son profil avec boutton modiffier
    @GetMapping("/MonProfil")
    public String monProfil(Model model){
        //aller chercher l'utiliosateur connecté


//        model.addAttribute("utilisateur", utilisateur);

        return "mon_profil";
    }





//  page profil a modiffier avec boutton update ou supprimer le compte
    @GetMapping("/MonProfil/update")
    public String updateProfil(){

        return "mon_profil_update";
    }
}
