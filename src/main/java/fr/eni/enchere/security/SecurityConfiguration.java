package fr.eni.enchere.security;

import org.springframework.boot.servlet.filter.OrderedHiddenHttpMethodFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
//    realisation du bcrypt
    private final DataSource dataSource;

    public SecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }





    //mise en place de la gestion des droits en fonction des pages affichées
    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http, OrderedHiddenHttpMethodFilter orderedHiddenHttpMethodFilter) throws Exception {
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //c'est ici que l'on va authoriser les chemins en fonction des utilisateurs
        http.authorizeHttpRequests(auth -> {
            //authoriser l'accès à la liste des glaces aux employés
            //accès au chemin /icecream en Get pour les employé
            auth.
                    /* les changements c'est ici le reste ne change pas il n'y a pas de raison
                     *********************************************    */

                     requestMatchers(HttpMethod.GET, "/encheres").permitAll()
                    .requestMatchers(HttpMethod.GET, "/encheres/filtres").permitAll()
                    .requestMatchers(HttpMethod.POST, "/encheres/filtres").permitAll()
                    //accès au chemin /encheres/add en Get pour les utilisateurs
                    .requestMatchers(HttpMethod.GET, "/encheres/add").hasRole("UTILISATEUR")
                    .requestMatchers(HttpMethod.POST, "/encheres/save").hasRole("UTILISATEUR")
                    .requestMatchers(HttpMethod.GET, "/encheres/details").hasRole("UTILISATEUR")
                    .requestMatchers(HttpMethod.POST, "/encheres/encherir").hasRole("UTILISATEUR")

                    // add by Raman pour la modification et suppression d'une enchere
                    .requestMatchers(HttpMethod.GET, "/encheres/placer").hasRole("UTILISATEUR")
                    .requestMatchers(HttpMethod.POST, "/encheres/placer").hasRole("UTILISATEUR")

                    .requestMatchers(HttpMethod.GET, "/encheres/update").hasRole("UTILISATEUR")
                    .requestMatchers(HttpMethod.GET, "/encheres/delete").hasRole("UTILISATEUR")
                    .requestMatchers(HttpMethod.GET, "/encherir").hasRole("UTILISATEUR")
                    .requestMatchers(HttpMethod.GET, "/profil").hasRole("UTILISATEUR")
                    .requestMatchers(HttpMethod.GET, "/MonProfil").hasRole("UTILISATEUR")
                    .requestMatchers(HttpMethod.GET, "/MonProfil/update").hasRole("UTILISATEUR")
                    .requestMatchers(HttpMethod.POST, "/MonProfil/update").hasRole("UTILISATEUR")
                    .requestMatchers(HttpMethod.GET, "/MonProfil/delete").hasRole("UTILISATEUR")
                    //donne à tous la permission de s'inscrire
                    .requestMatchers(HttpMethod.GET, "/inscription").permitAll()
                    .requestMatchers(HttpMethod.POST, "/inscription").permitAll()
                    //donne à tous la permissions de se connecter
                    .requestMatchers(HttpMethod.GET, "/login").permitAll()
                    // donne à tous la permission d'aller sur mot de passe oubliée et d'envoyer une requette de reinitialisation de mot de passe
                    .requestMatchers(HttpMethod.GET, "/MonProfil/MotDePasseOublie").permitAll()
                    .requestMatchers(HttpMethod.POST, "/MonProfil/MotDePasseOublie").permitAll()



                    //donne à tous la permission sur la page d'accueil
                    .requestMatchers("/*").permitAll()
                    //donner acces au css
                    .requestMatchers("/css/*").permitAll()
                    //donner acces au image
                    .requestMatchers("/image/*").permitAll()
                    //tous ce qui n'est pas spécifié n'est pas accessible
                    .anyRequest().denyAll();
        });
//CONFIGURATION DE LA GESTION DE SESSIONS (Raman)---------------------------------------------------
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation().migrateSession()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/login?expired")
        );



        // Ignore CSRF pour upload
        http.csrf(csrf -> csrf.disable());

//gestion du login
        http.formLogin( form -> {
                    //donne l'accès à la page de login à tous
                    form.loginPage("/login").permitAll();
                    //redirige après le login sur la page d'accueil
                    form.defaultSuccessUrl("/", true); //force la redirection vers la page d'accueil
//                    form.loginPage("/login").permitAll()
//                    //redirige après le login sur la page d'accueil
//                    .defaultSuccessUrl("/");
                }
        );

        http.logout( logout -> {
            logout.logoutUrl("/logout")
                    .logoutSuccessUrl("/");
        });

        // >>> Activation du remember-me <<<
        http.rememberMe(remember -> remember
                .key("cleSecreteUniquePourTonAppli")  // mets une chaîne bien unique
                .tokenValiditySeconds(1209600));

        return http.build();
    }
}
