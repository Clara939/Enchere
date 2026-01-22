package fr.eni.enchere.bo;

import java.time.LocalDate;

public class Encheres {
    private long id_enchere;
    private LocalDate date_enchere;
    private int montant_enchere;

    private Utilisateurs encherisseur;
    private Articles articles;

    public Encheres() {}

    public Encheres(long id_enchere, LocalDate date_enchere, int montant_enchere, Utilisateurs encherisseur, Articles articles) {
        this.id_enchere = id_enchere;
        this.date_enchere = date_enchere;
        this.montant_enchere = montant_enchere;
        this.encherisseur = encherisseur;
        this.articles = articles;
    }

    public Encheres(LocalDate date_enchere, int montant_enchere, Utilisateurs encherisseur, Articles articles) {
        this.date_enchere = date_enchere;
        this.montant_enchere = montant_enchere;
        this.encherisseur = encherisseur;
        this.articles = articles;
    }

    public long getId_enchere() {
        return id_enchere;
    }

    public void setId_enchere(long id_enchere) {
        this.id_enchere = id_enchere;
    }

    public LocalDate getDate_enchere() {
        return date_enchere;
    }

    public void setDate_enchere(LocalDate date_enchere) {
        this.date_enchere = date_enchere;
    }

    public Utilisateurs getEncherisseur() {
        return encherisseur;
    }

    public void setEncherisseur(Utilisateurs encherisseur) {
        this.encherisseur = encherisseur;
    }

    public int getMontant_enchere() {
        return montant_enchere;
    }

    public void setMontant_enchere(int montant_enchere) {
        this.montant_enchere = montant_enchere;
    }

    public Articles getArticles() {
        return articles;
    }

    public void setArticles(Articles articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "Encheres{" +
                "id_enchere=" + id_enchere +
                ", date_enchere=" + date_enchere +
                ", montant_enchere=" + montant_enchere +
                ", encherisseur=" + encherisseur +
                ", articles=" + articles +
                '}';
    }
}
