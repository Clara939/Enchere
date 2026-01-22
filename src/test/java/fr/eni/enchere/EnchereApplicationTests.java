package fr.eni.enchere;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.repository.UtilisateurRepositorySql;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EnchereApplicationTests {
    @Autowired
    UtilisateurRepositorySql utilisateurDAO;

    @Test
    void testCreateUtilisateur() {
        utilisateurDAO.createUtilisateur(new Utilisateur("user1", "Dupont", "Jean", "jean.dupont@example.com", "0612345678", "12 rue des Lilas", "75001", "Paris", "motdepasse123", 0, true, true));

        utilisateurDAO.createUtilisateur(new Utilisateur("user2", "Martin", "Claire", "claire.martin@example.com", "0623456789", "45 avenue Victor Hugo", "69002", "Lyon", "password456", 0, false, true));

        utilisateurDAO.createUtilisateur(new Utilisateur("user3", "Bernard", "Luc", "luc.bernard@example.com", "0634567890", "8 boulevard Voltaire", "13001", "Marseille", "secret789", 0, false, false));
    }

}
