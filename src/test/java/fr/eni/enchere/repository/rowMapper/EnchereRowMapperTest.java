package fr.eni.enchere.repository.rowMapper;

import fr.eni.enchere.bo.Enchere;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EnchereRowMapperTest {

    @Test
    void mapRow_happyPath() throws Exception {
        ResultSet rs = mock(ResultSet.class);

        when(rs.getLong("id_enchere")).thenReturn(1L);
        when(rs.getTimestamp("date_enchere")).thenReturn(Timestamp.valueOf("2026-01-25 10:00:00"));
        when(rs.getInt("montant_enchere")).thenReturn(200);

        when(rs.getLong("id_encherisseur")).thenReturn(5L);
        when(rs.getNString("nom_encherisseur")).thenReturn("Dupont");
        when(rs.getString("prenom_encherisseur")).thenReturn("Jean");
        when(rs.getString("email_encherisseur")).thenReturn("jean.dupont@example.com");

        when(rs.getLong("id_article")).thenReturn(2L);
        when(rs.getString("nom_article")).thenReturn("bureau");
        when(rs.getString("description")).thenReturn("magnifique bureau en bois");
        when(rs.getInt("prix_vente")).thenReturn(150);
        when(rs.getString("etat_vente")).thenReturn("vendu");
        when(rs.getDate("date_debut_encheres")).thenReturn(Date.valueOf(LocalDate.of(2026,1,24)));
        when(rs.getDate("date_fin_encheres")).thenReturn(Date.valueOf(LocalDate.of(2026,1,31)));

        Enchere e = new EnchereRowMapper().mapRow(rs, 0);

        assertEquals(1L, e.getId_enchere());
        assertEquals(LocalDate.of(2026,1,25), e.getDate_enchere());
        assertEquals(200, e.getMontant_enchere());

        assertNotNull(e.getEncherisseur());
        assertEquals(5L, e.getEncherisseur().getId_utilisateur());
        assertEquals("Dupont", e.getEncherisseur().getNom());

        assertNotNull(e.getArticles());
        assertEquals(2L, e.getArticles().getId_article());
        assertEquals("bureau", e.getArticles().getNom_article());
    }

    @Test
    void mapRow_withoutEncherisseurAndArticle() throws Exception {
        ResultSet rs = mock(ResultSet.class);

        when(rs.getLong("id_enchere")).thenReturn(2L);
        when(rs.getTimestamp("date_enchere")).thenReturn(Timestamp.valueOf("2026-01-26 11:00:00"));
        when(rs.getInt("montant_enchere")).thenReturn(50);

        when(rs.getLong("id_encherisseur")).thenReturn(0L);
        when(rs.getLong("id_article")).thenReturn(0L);

        Enchere e = new EnchereRowMapper().mapRow(rs, 0);

        assertEquals(2L, e.getId_enchere());
        assertEquals(LocalDate.of(2026,1,26), e.getDate_enchere());
        assertEquals(50, e.getMontant_enchere());

        assertNull(e.getEncherisseur());
        assertNull(e.getArticles());
    }
}
