package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Enchere;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnchereRepositorySql implements EnchereRepository{

        JdbcTemplate jdbcTemplate;
        NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public EnchereRepositorySql(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void createEnchere(Enchere enchere) {

    }

    @Override
    public List<Enchere> readAll() {
        String sql = "select * from Encheres";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Enchere.class));
    }

    @Override
    public Enchere readById(long id) {
        return null;
    }

    @Override
    public void updateEnchere(Enchere enchere) {

    }

    @Override
    public void deleteEnchere(long id) {

    }
}
