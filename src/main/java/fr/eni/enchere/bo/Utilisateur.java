package fr.eni.enchere.bo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur {
    private long id_utilisateur;
    @Pattern(regexp = "^[a-zA-Z0-9]+$",
            message = "Le nom ne peut contenir que des lettres ou des chiffres")
    @Size(min = 3, max = 30, message = "Le pseudo doit avoir entre 3 et 30 caractères")
    @NotBlank(message = "champ obligatoire")
    private String pseudo;
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\-]+$",
            message = "Le nom ne peut contenir que des lettres ou le tiret (-)")
    @Size(min = 2, max = 30, message = "Le nom doit avoir entre 2 et 30 caractères")
    @NotBlank(message = "champ obligatoire")
    private String nom;
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\-]+$",
            message = "Le nom ne peut contenir que des lettres ou le tiret (-)")
    @Size(min = 2, max = 30, message = "Le prénom doit avoir entre 2 et 30 caractères")
    @NotBlank(message = "champ obligatoire")
    private String prenom;

    @Email(message = "merci d'indiquer une adresse mail valide")
    @Size(max=100, message = "l'adresse mail doit contenir moinsde 100 caractères")
    private String email;
    @Pattern(regexp = "^[0-9]+$", message = "Le téléphone n'est composé que de chiffres")
    @Size(min = 2, max = 15, message="15 chiffres maximum")
    private String telephone;
    @Size(min = 2, max = 55)
    @NotBlank(message = "champ obligatoire")
    private String rue;
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "le code postal n'est composé que de chiffres et de lettres")
    @Size(min = 2, max = 10)
    @NotBlank(message = "champ obligatoire")
    private String code_postal;
    @Size(min = 2, max = 30)
    @NotBlank(message = "champ obligatoire")
    private String ville;
    @Pattern(regexp = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}",
            message = "Le mot de passe doit contenir au moins 8 caractères, avec au moins 1 majuscule, 1 minuscule, 1 chiffre et 1 caractère spécial parmi #?!@$%^&*-")
    @Size(min = 8, max = 255)
    @NotBlank(message = "champ obligatoire")
    private String mot_de_passe;
    private int credit = 0;
    private boolean administrateur =false;
    private boolean actif =true;


    public Utilisateur() { }

    public Utilisateur(String pseudo, String nom, String prenom, String telephone, String email, String rue, String code_postal, String ville, String mot_de_passe, int credit, boolean administrateur, boolean actif) {
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.rue = rue;
        this.code_postal = code_postal;
        this.ville = ville;
        this.mot_de_passe = mot_de_passe;
        this.credit = credit;
        this.administrateur = administrateur;
        this.actif = actif;
    }

    public Utilisateur(long id_utilisateur, String pseudo, String nom, String prenom, String telephone, String email, String rue, String code_postal, String ville, String mot_de_passe, int credit, boolean administrateur, boolean actif) {
        this.id_utilisateur = id_utilisateur;
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.rue = rue;
        this.code_postal = code_postal;
        this.ville = ville;
        this.mot_de_passe = mot_de_passe;
        this.credit = credit;
        this.administrateur = administrateur;
        this.actif = actif;
    }

    public long getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(long id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(boolean administrateur) {
        this.administrateur = administrateur;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id_utilisateur=" + id_utilisateur +
                ", pseudo='" + pseudo + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", rue='" + rue + '\'' +
                ", code_postal='" + code_postal + '\'' +
                ", ville='" + ville + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", credit=" + credit +
                ", administrateur=" + administrateur +
                ", actif=" + actif +
                '}';
    }
}
