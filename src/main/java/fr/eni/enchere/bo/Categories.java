package fr.eni.enchere.bo;

public class Categories {
    private long id_categorie;
    private String libelle;

    public Categories() {}

    public Categories(long id_categorie, String libelle) {
        this.id_categorie = id_categorie;
        this.libelle = libelle;
    }

    public Categories(String libelle) {
        this.libelle = libelle;
    }

    public long getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(long id_categorie) {
        this.id_categorie = id_categorie;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "id_categorie=" + id_categorie +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
