package fr.eni.enchere.security;

import fr.eni.enchere.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {
   private final DataSource dataSource;
   private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        String sql = """
            SELECT u.pseudo, u.mot_de_passe, u.actif 
            FROM Utilisateurs u 
            WHERE u.pseudo = ? OR u.email = ?
            """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, identifier);
            stmt.setString(2, identifier);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String pseudo = rs.getString("pseudo");
                    String password = rs.getString("mot_de_passe");
                    boolean enabled = rs.getBoolean("actif");

                    System.out.println(" [LOGIN] Utilisateur trouvé: " + pseudo);
                    System.out.println("   → actif: " + enabled);
                    //System.out.println("   → hash: " + password.substring(0, 20) + "..."); //ici erreur si le mot de passe est inférieur à 20 caractères
                    //System.out.println("   → hash: " + (password == null ? "NULL" : password.length() < 20 ? password : password.substring(0, 20)) + "...");


                    List<GrantedAuthority> authorities = getAuthorities(pseudo);

                    return new User(pseudo, password, enabled, true, true, true, authorities);
                }
                System.out.println(" [LOGIN] AUCUN utilisateur trouvé pour: " + identifier);
                throw new UsernameNotFoundException("User not found: " + identifier);
            }
        } catch (SQLException e) {
            System.err.println(" [LOGIN] ERREUR SQL: " + e.getMessage());
            throw new UsernameNotFoundException("Database error", e);
        }
    }

    private List<GrantedAuthority> getAuthorities(String pseudo) throws SQLException {
        String sql = "SELECT role FROM Roles WHERE pseudo = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pseudo);
            ResultSet rs = stmt.executeQuery();
            List<GrantedAuthority> authorities = new ArrayList<>();
            while (rs.next()) {
                authorities.add(new SimpleGrantedAuthority(rs.getString("role")));
            }
            return authorities;
        }
    }
}
