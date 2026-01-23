package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Enchere;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

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

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "insert into Encheres (date_enchere, montant_enchere, id_encherisseur, article " +
                    "values (:date_enchere, :montant_enchere, :encherisseur, :article)";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("date_enchere", enchere.getDate_enchere());
        map.addValue("montant_enchere", enchere.getMontant_enchere());
        map.addValue("encherisseur", enchere.getEncherisseur());
        map.addValue("article", enchere.getArticles());

        namedParameterJdbcTemplate.update(sql, map, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        enchere.setId_enchere(id);
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
