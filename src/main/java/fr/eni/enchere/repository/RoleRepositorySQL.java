package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Retrait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.management.relation.Role;
import java.util.List;

public class RoleRepositorySQL implements RoleRepository {

//    @Autowired
//    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//    @Override
//    public List<Role> readAll() {
//        String sql = "SELECT * FROM Roles";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Role.class));
//    }

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

    }

    @Override
    public void updateRole(Role role) {

    }

    @Override
    public void deleteRole(long id) {

    }
}
