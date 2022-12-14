package com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.repoutilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.entite.Utilisateur;

@Repository
public interface UtilisateurJpaRepo extends JpaRepository<Utilisateur, Long> {

}
