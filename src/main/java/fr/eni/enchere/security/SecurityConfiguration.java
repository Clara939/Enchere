package fr.eni.enchere.security;

import org.springframework.boot.servlet.filter.OrderedHiddenHttpMethodFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbc = new JdbcUserDetailsManager(dataSource);

        //détermine quelles informations utiliser pour la connexion
        //requete utilisée pour l'utilisateur
        jdbc.setUsersByUsernameQuery("select pseudo, mot_de_passe, actif from Utilisateurs where pseudo = ?");

        //requete utilisée pour le role
        jdbc.setAuthoritiesByUsernameQuery("select pseudo, role from Roles where pseudo = ?");

        return jdbc;
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
                    /*accès au chemin /encheres/add en Get pour les utilisateurs */
                    .requestMatchers(HttpMethod.GET, "/encheres/add").hasRole("UTILISATEUR")
                    .requestMatchers(HttpMethod.POST, "/encheres/save").hasRole("UTILISATEUR")
                    .requestMatchers(HttpMethod.GET, "/encheres/details").hasRole("UTILISATEUR")
                    .requestMatchers(HttpMethod.POST, "/encheres/encherir").hasRole("UTILISATEUR")
                    .requestMatchers(HttpMethod.GET, "/encheres/update").hasRole("UTILISATEUR")
                    .requestMatchers(HttpMethod.GET, "/encheres/delete").hasRole("UTILISATEUR")
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

                    /* *********************************************
                     jusqu'à là */


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
                .sessionFixation().migrateSession() // Protection contre la fixation de session
                                                   // Chaque authentification crée une nouvelle session et les anciennes données sont copiées.
                .maximumSessions(1)                 // Limite à une seule session par utilisateur
                                                   // Si l'utilisateur se connecte depuis un autre appareil, la session précédente est interrompue.
                .maxSessionsPreventsLogin(false)   // false = une nouvelle session remplace l'ancienne
                                                  // true = bloque les nouvelles connexions s'il existe déjà une session active
                .expiredUrl("/login?expired")       // Où rediriger l'utilisateur en cas d'expiration de la session
                                                   // L'utilisateur verra un message indiquant que sa session a expiré
        );

        // DÉFINITION DU DÉLAI D'EXPIRATION DE LA SESSION (5 MINUTES)
        http.sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Crée une session uniquement si nécessaire
            .invalidSessionUrl("/login?invalid")                     // Redirection si la session est invalid
                                                                    // Si la session est corrompue ou n'existe pas
            .sessionConcurrency(concurrency -> concurrency
                .maximumSessions(1)                               // Une seule session  parutilisateur
                .expiredUrl("/login?expired")                   // Page à l'expiration de la session
            )
        );

        // Définir un délai d'inactivité (5 minutes = 300 secondes)
        http.sessionManagement(session -> session
            .sessionFixation().migrateSession()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
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
