package com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.entite.Utilisateur;

@Repository
public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {

}
