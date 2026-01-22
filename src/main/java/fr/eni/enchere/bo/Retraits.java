package fr.eni.enchere.bo;

public class Retraits {
    private String rue;
    private String code_postal;
    private String ville;
    private long id_retrait;

    public Retraits() { }

    public Retraits(String code_postal, String rue, String ville, long id_retrait) {
        this.code_postal = code_postal;
        this.rue = rue;
        this.ville = ville;
        this.id_retrait = id_retrait;
    }

    public Retraits(String rue, String code_postal, String ville) {
        this.rue = rue;
        this.code_postal = code_postal;
        this.ville = ville;
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
                "rue='" + rue + '\'' +
                ", code_postal='" + code_postal + '\'' +
                ", ville='" + ville + '\'' +
                ", id_retrait=" + id_retrait +
                '}';
    }
}
