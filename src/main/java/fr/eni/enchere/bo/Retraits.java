package fr.eni.enchere.bo;

public class Retraits {
    private long id_article;
    private String rue;
    private String code_postal;
    private String ville;
    private long id_retrait;

    public Retraits() { }

    public Retraits(long id_article, String rue, String code_postal, String ville, long id_retrait) {
        this.id_article = id_article;
        this.rue = rue;
        this.code_postal = code_postal;
        this.ville = ville;
        this.id_retrait = id_retrait;
    }

    public Retraits(String rue, String code_postal, String ville, long id_article) {
        this.rue = rue;
        this.code_postal = code_postal;
        this.ville = ville;
        this.id_article = id_article;
    }

    public Retraits(long id_retrait, String ville, String rue, String code_postal) {
        this.id_retrait = id_retrait;
        this.ville = ville;
        this.rue = rue;
        this.code_postal = code_postal;
    }

    public Retraits(String rue, String code_postal, String ville) {
        this.rue = rue;
        this.code_postal = code_postal;
        this.ville = ville;
    }

    public long getId_article() {
        return id_article;
    }

    public void setId_article(long id_article) {
        this.id_article = id_article;
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

    public long getId_retrait() {
        return id_retrait;
    }

    public void setId_retrait(long id_retrait) {
        this.id_retrait = id_retrait;
    }

    @Override
    public String toString() {
        return "Retraits{" +
                "id_article=" + id_article +
                ", rue='" + rue + '\'' +
                ", code_postal='" + code_postal + '\'' +
                ", ville='" + ville + '\'' +
                ", id_retrait=" + id_retrait +
                '}';
    }
}
