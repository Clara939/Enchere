package fr.eni.enchere.bo;

import java.util.List;

public class Utilisateurs {
    private long id_utilisateur;
    private String pseudo;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String rue;
    private String code_postal;
    private String ville;
    private String mot_de_passe;
    private int credit;
    private boolean administrateur;
    private boolean actif;

    private List<Articles>articlesList;

    public Utilisateurs() { }

    public Utilisateurs(long id_utilisateur, String pseudo, String nom, String prenom, String email, String telephone, String rue, String code_postal, String ville, String mot_de_passe, int credit, boolean administrateur, boolean actif, List<Articles> articlesList) {
        this.id_utilisateur = id_utilisateur;
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.rue = rue;
        this.code_postal = code_postal;
        this.ville = ville;
        this.mot_de_passe = mot_de_passe;
        this.credit = credit;
        this.administrateur = administrateur;
        this.actif = actif;
        this.articlesList = articlesList;
    }

    public Utilisateurs(String pseudo, String nom, String prenom, String email, String telephone, String rue, String code_postal, String ville, String mot_de_passe, int credit, boolean administrateur, boolean actif, List<Articles> articlesList) {
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.rue = rue;
        this.code_postal = code_postal;
        this.ville = ville;
        this.mot_de_passe = mot_de_passe;
        this.credit = credit;
        this.administrateur = administrateur;
        this.actif = actif;
        this.articlesList = articlesList;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<Articles> getArticlesList() {
        return articlesList;
    }

    public void setArticlesList(List<Articles> articlesList) {
        this.articlesList = articlesList;
    }

    @Override
    public String toString() {
        return "Utilisateurs{" +
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
                ", articlesList=" + articlesList +
                '}';
    }
}
