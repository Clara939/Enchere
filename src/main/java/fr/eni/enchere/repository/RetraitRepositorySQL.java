package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Retrait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Objects;


@Repository
public class RetraitRepositorySQL implements RetraitRepository {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;

    // Retrieve all Retraits
    @Override
    public List<Retrait> readAll() {
        String sql = "SELECT * FROM Retraits";
        // Execute the query and map the results to a list of Retrait objects
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Retrait.class));
    }

    // Retrieve a Retrait by ID
    @Override
    public Retrait readRetraitById(long id) {
        String sql = "SELECT * FROM Retraits WHERE id_retrait = :id_retrait"; // Adjusted to match the field name in Retrait class
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();  // Create parameter source
        parameterSource.addValue("id_retrait", id); // Add the id parameter
        // Execute the query and map the result to a Retrait object
        return namedParameterJdbcTemplate.queryForObject(sql, parameterSource,
                new BeanPropertyRowMapper<>(Retrait.class));
    }

    @Override
    public void createRetrait(Retrait retrait) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder(); // To hold the generated key
        String sql = "INSERT INTO Retraits (rue, code_postal, ville) " +
                "VALUES (:rue, :code_postal, :ville)";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("rue", retrait.getRue());
        parameterSource.addValue("code_postal", retrait.getCode_postal());
        parameterSource.addValue("ville", retrait.getVille());
        // Execute the insert operation
        namedParameterJdbcTemplate.update(sql, parameterSource, keyHolder);
        // Retrieve the generated id
        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        // Set the generated id back to the Retrait object
        retrait.setId_retrait(id);
    }

    @Override
    public void updateRetrait(Retrait retrait) {

    }

    @Override
    public void deleteRetrait(long id) {

    }
}
