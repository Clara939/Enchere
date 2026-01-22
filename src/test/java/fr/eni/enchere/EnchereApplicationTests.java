package fr.eni.enchere;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.repository.UtilisateurRepositorySql;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.repository.CategorieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class EnchereApplicationTests {

    @Autowired
    UtilisateurRepositorySql utilisateurDAO;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    CategorieRepository categorieRepository;

    // TEST DE UTILISATEUR ----------------------------------------------------------------------------------------------------------------------------------------------------
    @Test
    void testCreateUtilisateur() {
        utilisateurDAO.createUtilisateur(new Utilisateur("user1", "Dupont", "Jean", "0612345678","jean.dupont@example.com",  "12 rue des Lilas", "75001", "Paris", "motdepasse123", 0, true, true));

        utilisateurDAO.createUtilisateur(new Utilisateur("user2", "Martin", "Claire","0623456789", "claire.martin@example.com",  "45 avenue Victor Hugo", "69002", "Lyon", "password456", 0, false, true));

        utilisateurDAO.createUtilisateur(new Utilisateur("user3", "Bernard", "Luc",  "0634567890","luc.bernard@example.com", "8 boulevard Voltaire", "13001", "Marseille", "secret789", 0, false, false));
    }

    @Test
    void testReadAllUtilisateur() {
        utilisateurDAO.readAll().forEach(System.out::println);
    }

    @Test
    void testReadByIdUtilisateur(){
        System.out.println(utilisateurDAO.readById(2));
    }

    @Test
    void testUpdateUtilisateur(){
        utilisateurDAO.updateUtilisateur(new Utilisateur(2, "user2", "Marie", "Claire", "0623456789","claire.marie@example.com",  "45 avenue Victor Hugo", "69002", "Lyon", "password456", 0, false, true));
    }

    @Test
    void testDeleteUtilisateur(){
        utilisateurDAO.deleteUtilisateur(3);
    }

    // TEST DE CATEGORIE -------------------------------------------------------------------------------------------------------------------------------------------------------
    @Test
    void createTables() {
        jdbcTemplate.execute("IF OBJECT_ID('CATEGORIE', 'U') IS NOT NULL DROP TABLE Categories");

        jdbcTemplate.execute("create table Categories( " +
                "id_categorie bigint identity constraint categorie_pk primary key, " +
                "libelle varchar(30) not null unique)");
    }


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


}

