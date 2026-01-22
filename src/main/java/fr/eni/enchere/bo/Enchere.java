package fr.eni.enchere.bo;

import java.time.LocalDate;

public class Enchere {
    private long id_enchere;
    private LocalDate date_enchere;
    private int montant_enchere;

    private Utilisateur encherisseur;
    private Article articles;

    public Enchere() {}

    public Enchere(long id_enchere, LocalDate date_enchere, int montant_enchere, Utilisateur encherisseur, Article articles) {
        this.id_enchere = id_enchere;
        this.date_enchere = date_enchere;
        this.montant_enchere = montant_enchere;
        this.encherisseur = encherisseur;
        this.articles = articles;
    }

    public Enchere(LocalDate date_enchere, int montant_enchere, Utilisateur encherisseur, Article articles) {
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

    public Utilisateur getEncherisseur() {
        return encherisseur;
    }

    public void setEncherisseur(Utilisateur encherisseur) {
        this.encherisseur = encherisseur;
    }

    public int getMontant_enchere() {
        return montant_enchere;
    }

    public void setMontant_enchere(int montant_enchere) {
        this.montant_enchere = montant_enchere;
    }

    public Article getArticles() {
        return articles;
    }

    public void setArticles(Article articles) {
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
