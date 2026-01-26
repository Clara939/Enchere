package fr.eni.enchere.controller.converter;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.service.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String inscrireUtilisateur(@ModelAttribute(name = "utilisateur") Utilisateur utilisateur){
        utilisateurService.createUtilisateur(utilisateur);

        return "redirect: /";
    }


//
}
