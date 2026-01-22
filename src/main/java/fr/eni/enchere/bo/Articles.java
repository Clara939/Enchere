package fr.eni.enchere.bo;

import java.time.LocalDate;

public class Articles {
    private long id_article;
    private String nom_article;
    private String description;
    private LocalDate date_debut_encheres;
    private LocalDate date_fin_encheres;
    private int prix_initial;
    private int prix_vente;
    private String etat_vente;

    private Retraits lieuxRetrait;
    private Categories categorieArticle;
    private Utilisateurs vendeur;

    public Articles() {}

    public Articles(long id_article, String nom_article, String description, LocalDate date_debut_encheres, LocalDate date_fin_encheres, int prix_initial, int prix_vente, String etat_vente, Retraits lieuxRetrait, Categories categorieArticle, Utilisateurs vendeur) {
        this.id_article = id_article;
        this.nom_article = nom_article;
        this.description = description;
        this.date_debut_encheres = date_debut_encheres;
        this.date_fin_encheres = date_fin_encheres;
        this.prix_initial = prix_initial;
        this.prix_vente = prix_vente;
        this.etat_vente = etat_vente;
        this.lieuxRetrait = lieuxRetrait;
        this.categorieArticle = categorieArticle;
        this.vendeur = vendeur;
    }

    public Articles(String nom_article, String description, LocalDate date_debut_encheres, LocalDate date_fin_encheres, int prix_initial, int prix_vente, String etat_vente, Retraits lieuxRetrait, Categories categorieArticle, Utilisateurs vendeur) {
        this.nom_article = nom_article;
        this.description = description;
        this.date_debut_encheres = date_debut_encheres;
        this.date_fin_encheres = date_fin_encheres;
        this.prix_initial = prix_initial;
        this.prix_vente = prix_vente;
        this.etat_vente = etat_vente;
        this.lieuxRetrait = lieuxRetrait;
        this.categorieArticle = categorieArticle;
        this.vendeur = vendeur;
    }

    public long getId_article() {
        return id_article;
    }

    public void setId_article(long id_article) {
        this.id_article = id_article;
    }

    public String getNom_article() {
        return nom_article;
    }

    public void setNom_article(String nom_article) {
        this.nom_article = nom_article;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate_debut_encheres() {
        return date_debut_encheres;
    }

    public void setDate_debut_encheres(LocalDate date_debut_encheres) {
        this.date_debut_encheres = date_debut_encheres;
    }

    public LocalDate getDate_fin_encheres() {
        return date_fin_encheres;
    }

    public void setDate_fin_encheres(LocalDate date_fin_encheres) {
        this.date_fin_encheres = date_fin_encheres;
    }

    public int getPrix_initial() {
        return prix_initial;
    }

    public void setPrix_initial(int prix_initial) {
        this.prix_initial = prix_initial;
    }

    public int getPrix_vente() {
        return prix_vente;
    }

    public void setPrix_vente(int prix_vente) {
        this.prix_vente = prix_vente;
    }

    public String getEtat_vente() {
        return etat_vente;
    }

    public void setEtat_vente(String etat_vente) {
        this.etat_vente = etat_vente;
    }

    public Retraits getLieuxRetrait() {
        return lieuxRetrait;
    }

    public void setLieuxRetrait(Retraits lieuxRetrait) {
        this.lieuxRetrait = lieuxRetrait;
    }

    public Categories getCategorieArticle() {
        return categorieArticle;
    }

    public void setCategorieArticle(Categories categorieArticle) {
        this.categorieArticle = categorieArticle;
    }

    public Utilisateurs getVendeur() {
        return vendeur;
    }

    public void setVendeur(Utilisateurs vendeur) {
        this.vendeur = vendeur;
    }
}
