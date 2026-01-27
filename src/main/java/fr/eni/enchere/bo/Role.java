package fr.eni.enchere.bo;

public class Role {
    private String pseudo;
    private String role;

    public Role(String pseudo, String role) {
        this.pseudo = pseudo;
        this.role = role;
    }

    public Role() {
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "pseudo='" + pseudo + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
