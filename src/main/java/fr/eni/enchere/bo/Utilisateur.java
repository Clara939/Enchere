package fr.eni.enchere.bo;

import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur {
    private long id_utilisateur;
    @Size(min = 3, max = 255)
    private String pseudo;
    @Size(min = 2, max = 255)
    private String nom;
    @Size(min = 2, max = 255)
    private String prenom;
    private String email;
    private String telephone;
    @Size(min = 2, max = 255)
    private String rue;
    @Size(min = 2, max = 10)
    private String code_postal;
    @Size(min = 2, max = 30)
    private String ville;
    @Size(min = 2, max = 255)
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
