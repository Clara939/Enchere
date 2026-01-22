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
    private Categorie categorieArticle;
    private Utilisateurs vendeur;


}
