package com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.repository;

import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.entite.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long>,RoleRepositoryCustom {

    @Query("SELECT r FROM Role r WHERE r.name= :roleName")
    Role findRoleByName(String roleName);

}
