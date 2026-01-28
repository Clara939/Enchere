package fr.eni.enchere.service;

import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.repository.RetraitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

@Service
public class RetraitServiceImpl implements RetraitService{
     @Autowired
    private JdbcTemplate jdbcTemplate;
     @Autowired
     private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    RetraitRepository retraitRepository;

    public RetraitServiceImpl(RetraitRepository retraitRepository) {
        this.retraitRepository = retraitRepository;
    }

    @Override
    public Retrait readRetraitById(long id) {
        String sql = "SELECT id_retrait, rue, code_postal, ville FROM Retraits WHERE id_retrait = :id";

        return namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource("id", id), (rs, rowNum) -> {
                    Retrait retrait = new Retrait();
                    retrait.setId_retrait(rs.getLong("id_retrait"));
                    retrait.setRue(rs.getString("rue"));
                    retrait.setCode_postal(rs.getString("code_postal"));
                    retrait.setVille(rs.getString("ville"));
                    return retrait;
                }); }

    @Override
    public void createRetrait(Retrait retrait) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO Retraits (rue, code_postal, ville) " +
                "VALUES (:rue, :code_postal, :ville)";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("rue", retrait.getRue());
        map.addValue("code_postal", retrait.getCode_postal());
        map.addValue("ville",retrait.getVille());

        namedParameterJdbcTemplate.update(sql, map, keyHolder);

        retrait.setId_retrait(keyHolder.getKey().longValue());
         }

    @Override
    public void updateRetrait(Retrait retrait) {
        String sql = "UPDATE Retraits SET rue = :rue, code_postal = :code_postal, ville = :ville WHERE id_retrait = :id";
        MapSqlParameterSource map = new MapSqlParameterSource();
                map.addValue("id", retrait.getId_retrait());
                map.addValue("rue", retrait.getRue());
                map.addValue("code_postal", retrait.getCode_postal());
                map.addValue("ville", retrait.getVille());
        namedParameterJdbcTemplate.update(sql, map); }

    @Override
    public void deleteRetrait(long id) {
        String sql = "DELETE FROM Retraits WHERE id_retrait = :id";
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id)); }
}
