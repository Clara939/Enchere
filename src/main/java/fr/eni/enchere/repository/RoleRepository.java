package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.bo.Role;

import java.util.List;

public interface RoleRepository {


    List<Role> readAll();

    Role readRoleById(long id);

    void createRole(Role role);

    void updateRole(Role role);

    void deleteRole(long id);
}
