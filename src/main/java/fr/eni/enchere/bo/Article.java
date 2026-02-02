package fr.eni.enchere.bo;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;



public class Article {
    private long id_article;
    @NotNull
    private String nom_article;
    private String description;
    @NotNull(message = "Début obligatoire")
    @FutureOrPresent(message = "Le début de la vente ne peut pas etre inferieur à la date du jour")
    private LocalDate date_debut_encheres;
    @NotNull(message = "Date de fin obligatoire")
    @FutureOrPresent(message = "La fin de la vente ne peut pas etre inferieur à la date du jour")
    private LocalDate date_fin_encheres;
    private Integer prix_initial;
    private Integer prix_vente;
        @NotNull
    private String etat_vente = "CREE";
    private String photoArticle;
    private Retrait lieuxRetrait;
    private Categorie categorieArticle;
    private Utilisateur vendeur;
    private Utilisateur acheteur;

    // fonction pour gerer les date d'enchere
    // pour la validation 1 jour minimum
    @AssertTrue(message = "Fin doit être au moins 1 jour après début")
    public boolean isFinAfterDebutPlusUnJour() {
        return date_debut_encheres != null &&
                date_fin_encheres != null &&
                !date_fin_encheres.isBefore(date_debut_encheres);
    }
    public Article() {}

    public Article(long id_article, String nom_article, String description, LocalDate date_debut_encheres, LocalDate date_fin_encheres, Integer prix_initial, Integer prix_vente, String etat_vente, String photoArticle, Retrait lieuxRetrait, Categorie categorieArticle, Utilisateur vendeur, Utilisateur acheteur) {
           this.id_article = id_article;
        this.nom_article = nom_article;
        this.description = description;
        this.date_debut_encheres = date_debut_encheres;
        this.date_fin_encheres = date_fin_encheres;
        this.prix_initial = prix_initial;
        this.prix_vente = prix_vente;
        this.etat_vente = etat_vente;
        this.photoArticle = photoArticle;
        this.lieuxRetrait = lieuxRetrait;
        this.categorieArticle = categorieArticle;
        this.vendeur = vendeur;
        this.acheteur = acheteur;
    }

    public Article(String nom_article, String description, LocalDate date_debut_encheres, LocalDate date_fin_encheres, Integer prix_initial, Integer prix_vente, String etat_vente, String photoArticle, Retrait lieuxRetrait, Categorie categorieArticle, Utilisateur vendeur, Utilisateur acheteur) {
        this.nom_article = nom_article;
        this.description = description;
        this.date_debut_encheres = date_debut_encheres;
        this.date_fin_encheres = date_fin_encheres;
        this.prix_initial = prix_initial;
        this.prix_vente = prix_vente;
        this.etat_vente = etat_vente;
        this.photoArticle = photoArticle;
        this.lieuxRetrait = lieuxRetrait;
        this.categorieArticle = categorieArticle;
        this.vendeur = vendeur;
        this.acheteur = acheteur;
    }

    //constructeur temporaire pour les test sans la photo
    public Article(String nom_article, String description, LocalDate date_debut_encheres, LocalDate date_fin_encheres, Integer prix_initial, Integer prix_vente, String etat_vente, Retrait lieuxRetrait, Categorie categorieArticle, Utilisateur vendeur, Utilisateur acheteur) {
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
        this.acheteur = acheteur;
    }

    public Article(long id_article, String nom_article, String description, LocalDate date_debut_encheres, LocalDate date_fin_encheres, Integer prix_initial, Integer prix_vente, String etat_vente, Retrait lieuxRetrait, Categorie categorieArticle, Utilisateur vendeur, Utilisateur acheteur) {
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
        this.acheteur = acheteur;
    }

    public Utilisateur getAcheteur() {
        return acheteur;
    }

    public void setAcheteur(Utilisateur acheteur) {
        this.acheteur = acheteur;
    }

    public Utilisateur getVendeur() {
        return vendeur;
    }

    public void setVendeur(Utilisateur vendeur) {
        this.vendeur = vendeur;
    }

    public Categorie getCategorieArticle() {
        return categorieArticle;
    }

    public void setCategorieArticle(Categorie categorieArticle) {
        this.categorieArticle = categorieArticle;
    }

    public Retrait getLieuxRetrait() {
        return lieuxRetrait;
    }

    public void setLieuxRetrait(Retrait lieuxRetrait) {
        this.lieuxRetrait = lieuxRetrait;
    }

    public String getEtat_vente() {
        return etat_vente;
    }

    public void setEtat_vente(String etat_vente) {
        this.etat_vente = etat_vente;
    }

    public Integer getPrix_initial() {
        return prix_initial;
    }

    public void setPrix_initial(Integer prix_initial) {
        this.prix_initial = prix_initial;
    }

    public Integer getPrix_vente() {
        return prix_vente;
    }

    public void setPrix_vente(Integer prix_vente) {
        this.prix_vente = prix_vente;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom_article() {
        return nom_article;
    }

    public void setNom_article(String nom_article) {
        this.nom_article = nom_article;
    }

    public long getId_article() {
        return id_article;
    }

    public void setId_article(long id_article) {
        this.id_article = id_article;
    }
    public String getPhotoArticle() {
        return photoArticle;
    }

    public void setPhotoArticle(String photoArticle) {
        this.photoArticle = photoArticle;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id_article=" + id_article +
                ", nom_article='" + nom_article + '\'' +
                ", description='" + description + '\'' +
                ", date_debut_encheres=" + date_debut_encheres +
                ", date_fin_encheres=" + date_fin_encheres +
                ", prix_initial=" + prix_initial +
                ", prix_vente=" + prix_vente +
                ", etat_vente='" + etat_vente + '\'' +
                ", lieuxRetrait=" + lieuxRetrait +
                ", categorieArticle=" + categorieArticle +
                ", vendeur=" + vendeur +
                ", acheteur=" + acheteur +
                '}';
    }
}
