package com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.repository;

import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.entite.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long>, UserRepositoryCustom {

  @Query("SELECT u FROM User u WHERE u.username= :username")
  User findUserByName(String username);
}
