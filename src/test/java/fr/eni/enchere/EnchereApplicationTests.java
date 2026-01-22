package fr.eni.enchere;

import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.repository.CategorieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class EnchereApplicationTests {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    CategorieRepository categorieRepository;

    @Test
    void  createTables(){
        jdbcTemplate.execute("IF OBJECT_ID('CATEGORIE', 'U') IS NOT NULL DROP TABLE CATEGORIE");

        jdbcTemplate.execute("create table Categories( " +
                        "id_categorie bigint identity constraint categorie_pk primary key, " +
                        "libelle varchar(30) not null unique)");
    }

    @Test
    void createCategorie(){
categorieRepository.create(new Categorie("Meubles"));
        categorieRepository.create(new Categorie("Electroménager"));
        categorieRepository.create(new Categorie("Décoration"));
    }

    @Test
    void readAllCategorie(){
        categorieRepository.readAll().forEach(System.out::println);
    }

    @Test
    void readByIdCategorie(){
        System.out.println(categorieRepository.readById(2));
    }

    @Test
    void deleteCategorie(){
categorieRepository.delete(2);
categorieRepository.readAll().forEach(System.out::println);
    }

    @Test
    void updateCategorie(){
        categorieRepository.update(new Categorie(1, "jouets"));
    }

}
