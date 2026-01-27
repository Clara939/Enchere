package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleRepositorySQL implements RoleRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Role> readAll() {
        return List.of();
    }

    @Override
    public Role readRoleById(long id) {
        return null;
    }

    @Override
    public void createRole(Role role) {
        String sql = "INSERT INTO [ROLES] ([pseudo],[role]) VALUES (:pseudo, :role)";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("pseudo", role.getPseudo());
        parameterSource.addValue("role", role.getRole());
        namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public void updateRole(Role role) {}

    @Override
    public void deleteRole(long id) {}
}
