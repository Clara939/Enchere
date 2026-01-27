package fr.eni.enchere.repository.rowMapper;

import fr.eni.enchere.bo.Enchere;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnchereRowMapper implements RowMapper<Enchere> {

    @Override
    public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
        Enchere enchereResult = new Enchere();


        return null;
    }
}
