package fr.eni.enchere.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
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
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //c'est ici que l'on va authoriser les chemins en fonction des utilisateurs
        http.authorizeHttpRequests(auth -> {
            //authoriser l'accès à la liste des glaces aux employés
            //accès au chemin /icecream en Get pour les employé
            auth.
                    /* les changements c'est ici le reste ne change pas il n'y a pas de raison
                     *********************************************    */

                            requestMatchers(HttpMethod.GET, "/encheres").hasRole("UTILISATEUR")
                    /*accès au chemin /encheres/add en Get pour les utilisateurs */
                    .requestMatchers(HttpMethod.GET, "/encheres/add").hasRole("UTILISATEUR")
                    .requestMatchers(HttpMethod.GET, "/profil").hasRole("UTILISATEUR")
                    .requestMatchers(HttpMethod.GET, "/login").permitAll()
                    .requestMatchers(HttpMethod.GET, "/inscription").permitAll()
                    .requestMatchers(HttpMethod.POST, "/inscription").permitAll()

                    /* *********************************************
                     jusqu'à là */
                    //donne à tous la permission de s'inscrire
                    .requestMatchers("/inscription").permitAll()
                    //donne à tous la permission sur la page d'accueil
                    .requestMatchers("/*").permitAll()
                    //donner acces au css
                    .requestMatchers("/css/*").permitAll()
                    //donner acces au image
                    .requestMatchers("/image/*").permitAll()
                    //tous ce qui n'est pas spécifié n'est pas accessible
                    .anyRequest().denyAll();
        });

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

        return http.build();
    }
}
