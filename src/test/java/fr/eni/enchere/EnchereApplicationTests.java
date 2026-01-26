package fr.eni.enchere;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.repository.*;
import fr.eni.enchere.bo.Categorie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;

@SpringBootTest
class EnchereApplicationTests {

    @Autowired
    UtilisateurRepository utilisateurDAO;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    CategorieRepository categorieRepository;
    @Autowired
    RetraitRepository retraitRepository;

    @Autowired
    EnchereRepository enchereDAO;

    @Test
    void dropTables() {
        jdbcTemplate.execute("IF OBJECT_ID('ENCHERES', 'U') IS NOT NULL DROP TABLE ENCHERES");
        jdbcTemplate.execute("IF OBJECT_ID('ARTICLES', 'U') IS NOT NULL DROP TABLE ARTICLES");
        jdbcTemplate.execute("IF OBJECT_ID('ROLES', 'U') IS NOT NULL DROP TABLE ROLES");
        jdbcTemplate.execute("IF OBJECT_ID('UTILISATEURS', 'U') IS NOT NULL DROP TABLE UTILISATEURS");
        jdbcTemplate.execute("IF OBJECT_ID('CATEGORIES', 'U') IS NOT NULL DROP TABLE CATEGORIES");
        jdbcTemplate.execute("IF OBJECT_ID('RETRAITS', 'U') IS NOT NULL DROP TABLE RETRAITS");
        jdbcTemplate.execute("create table Categories( " + "id_categorie bigint identity constraint categorie_pk primary key, " + "libelle varchar(30) not null unique)");
        jdbcTemplate.execute("create table Retraits\n " + "(\n " + "    id_retrait  bigint   identity   not null\n " + "        constraint retrait_pk\n " + "            primary key,\n " + "    rue         varchar(55) not null,\n " + "    code_postal varchar(10) not null,\n " + "    ville       varchar(30) not null\n " + ")");
        jdbcTemplate.execute("create table Utilisateurs\n " +
                "(\n " +
                "    id_utilisateur bigint identity\n " +
                "        constraint utilisateur_pk\n " +
                "            primary key,\n " +
                "    pseudo         varchar(30) not null\n " +
                "        unique,\n " +
                "    nom            varchar(30) not null,\n " +
                "    prenom         varchar(30) not null,\n " +
                "    email          varchar(100) not null\n " +
                "        unique,\n " +
                "    telephone      varchar(15),\n " +
                "    rue            varchar(55) not null,\n " +
                "    code_postal    varchar(10) not null,\n " +
                "    ville          varchar(30) not null,\n " +
                "    mot_de_passe   varchar(30) not null,\n " +
                "    credit         int         not null,\n " +
                "    administrateur bit         not null,\n " +
                "    actif          bit         not null\n " +
                ")");
        jdbcTemplate.execute("create table ROLES\n " +
                "(\n" +
                "    pseudo varchar(30) not null\n " +
                "        references Utilisateurs(pseudo),\n " +
                "    role   varchar(50)  not null,\n " +
                "    primary key (pseudo, role)\n " +
                ")");
        jdbcTemplate.execute("create table Articles\n " +
                "(\n " +
                "    id_article          bigint identity\n " +
                "        constraint articles_pk\n " +
                "            primary key,\n " +
                "    nom_article         varchar(30)  not null,\n " +
                "    description         varchar(300) not null,\n " +
                "    date_debut_encheres datetime     not null,\n " +
                "    date_fin_encheres   datetime     not null,\n " +
                "    prix_initial        int,\n " +
                "    prix_vente          int,\n " +
                "    etat_vente          varchar(30)  not null,\n " +
                "    id_vendeur          bigint       not null\n " +
                "        constraint ventes_utilisateur_fk\n " +
                "            references Utilisateurs,\n " +
                "    id_categorie        bigint       not null\n " +
                "        constraint ARTICLES_categories_fk\n " +
                "            references Categories,\n " +
                "    id_retrait          bigint       not null\n " +
                "        constraint ARTICLES_retraits_fk\n " +
                "            references Retraits,\n " +
                "    id_acheteur         bigint\n " +
                ")");
        jdbcTemplate.execute("create table Encheres\n " +
                "(\n " +
                "    id_enchere      bigint identity\n " +
                "        constraint enchere_pk\n " +
                "            primary key,\n " +
                "    id_encherisseur bigint   not null\n " +
                "        constraint encheres_UTILISATEURS_fk\n " +
                "            references Utilisateurs,\n " +
                "    id_article      bigint   not null\n " +
                "        constraint encheres_ARTICLES_fk\n " +
                "            references Articles,\n " +
                "    date_enchere    datetime not null,\n " +
                "    montant_enchere int      not null\n " +
                ")");
    }
    // TEST DE UTILISATEUR ----------------------------------------------------------------------------------------------------------------------------------------------------
    @Test
    void testCreateUtilisateur() {
        utilisateurDAO.createUtilisateur(new Utilisateur("user1", "Dupont", "Jean", "0612345678", "jean.dupont@example.com", "12 rue des Lilas", "75001", "Paris", "{noop}123", 0, true, true));

        utilisateurDAO.createUtilisateur(new Utilisateur("user2", "Martin", "Claire", "0623456789", "claire.martin@example.com", "45 avenue Victor Hugo", "69002", "Lyon", "{noop}456", 0, false, true));

        utilisateurDAO.createUtilisateur(new Utilisateur("user3", "Bernard", "Luc", "0634567890", "luc.bernard@example.com", "8 boulevard Voltaire", "13001", "Marseille", "{noop}789", 0, false, true));
    }



    @Test
    void testReadAllUtilisateur() {
        utilisateurDAO.readAll().forEach(System.out::println);
    }

    @Test
    void testReadByIdUtilisateur() {
        System.out.println(utilisateurDAO.readById(2));
    }

    @Test
    void testUpdateUtilisateur() {
        utilisateurDAO.updateUtilisateur(new Utilisateur(2, "user2", "Marie", "Claire", "0623456789", "claire.marie@example.com", "45 avenue Victor Hugo", "69002", "Lyon", "password456", 0, false, true));
    }

    @Test
    void testDeleteUtilisateur() {
        utilisateurDAO.deleteUtilisateur(3);
    }


    // TEST DE CATEGORIE -------------------------------------------------------------------------------------------------------------------------------------------------------


    @Test
    void createCategorie() {
        categorieRepository.create(new Categorie("Meubles"));
        categorieRepository.create(new Categorie("Electroménager"));
        categorieRepository.create(new Categorie("Décoration"));
    }

    @Test
    void readAllCategorie() {
        categorieRepository.readAll().forEach(System.out::println);
    }

    @Test
    void readByIdCategorie() {
        System.out.println(categorieRepository.readById(2));
    }

    @Test
    void deleteCategorie() {
        categorieRepository.delete(2);
        categorieRepository.readAll().forEach(System.out::println);
    }

    @Test
    void updateCategorie() {
        categorieRepository.update(new Categorie(1, "jouets"));
    }

    //-------------------------- TEST DE RETRAIT -------------------------------------------------------------------------------------------------------------------------------------------------------

    @Test
    void testCreateRetrait() {
        retraitRepository.createRetrait(new Retrait("10 rue de la Paix", "75000", "Paris"));
        retraitRepository.createRetrait(new Retrait("20 avenue des Champs", "35000", "Rennes"));
        retraitRepository.createRetrait(new Retrait("5 boulevard Saint-Michel", "38000", "Rennes"));
    }

    @Test
    void testReadAllRetrait() {
        retraitRepository.readAll().forEach(System.out::println);
    }

    @Test
    void testReadByIdRetrait() {
        System.out.println(retraitRepository.readRetraitById(1));
    }

    @Test
    void testUpdateRetrait() {
        retraitRepository.updateRetrait(new Retrait("35000", "25 avenue des Champs", "Rennes", 2));
    }

    @Test
    void testDeleteRetrait() {
        retraitRepository.deleteRetrait(3);
    }

// AJOUTS DANS ROLES--------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Test
    void createRoles(){
        jdbcTemplate.execute("INSERT INTO [ROLES] ([pseudo],[role]) VALUES ('user1','ROLE_UTILISATEUR')");
        jdbcTemplate.execute("INSERT INTO [ROLES] ([pseudo],[role]) VALUES ('user2','ROLE_UTILISATEUR')");
        jdbcTemplate.execute("INSERT INTO [ROLES] ([pseudo],[role]) VALUES ('user2','ROLE_ADMIN')");
        jdbcTemplate.execute("INSERT INTO [ROLES] ([pseudo],[role]) VALUES ('user3','ROLE_UTILISATEUR')");
}


    // TEST DE ARTICLE -------------------------------------------------------------------------------------------------------------------------------------------------------

    @Test
    void createArticle(){
        articleRepository.create(new Article("bureau", "magnifique bureau en bois", LocalDate.of(2026, 1, 24), LocalDate.of(2026, 1, 31), 200, 200, "créé", retraitRepository.readRetraitById(2), categorieRepository.readById(1), utilisateurDAO.readById(2), null));
        articleRepository.create(new Article("cafetière", "n'accepte pas les dosettes", LocalDate.of(2026, 1, 3), LocalDate.of(2026, 1, 30), 50, 60, "vendu", retraitRepository.readRetraitById(1), categorieRepository.readById(3), utilisateurDAO.readById(1), utilisateurDAO.readById(2)));
    }

    @Test
    void updateArticle(){
        articleRepository.update(new Article(2,"cafetière", "n'accepte ni les dosettes ni le café en grains", LocalDate.of(2026, 1, 3), LocalDate.of(2026, 1, 30), 50, 60, "vendu", retraitRepository.readRetraitById(1), categorieRepository.readById(3), utilisateurDAO.readById(1), utilisateurDAO.readById(2)));
    }

    @Test
    void deleteArticle() {
        articleRepository.delete(1);
        articleRepository.readAll().forEach(System.out::println);
    }

        @Test
                void readAllArticle(){
            articleRepository.readAll().forEach(System.out::println);
        }

        @Test
        void readArticleById() {
            System.out.println(articleRepository.readById(2));
        }

// TEST DE ENCHERE-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    @Test
    void testReadAllEnchere() {
        enchereDAO.readAll().forEach(System.out::println);
    }





}
