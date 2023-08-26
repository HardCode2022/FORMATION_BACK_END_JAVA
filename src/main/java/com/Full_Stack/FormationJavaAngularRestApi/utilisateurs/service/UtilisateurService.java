package com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.service;

import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.entite.Utilisateur;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.repoutilisateur.UtilisateurJpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurJpaRepo utilisateurService;

    /**
     * Recuperation de l'ensemble des utilisateurs
     * @return list utilisateurs
     */
    public List<Utilisateur> recupererListeUtilisateur(){
        return (List<Utilisateur>) utilisateurService.findAll();
    }

    /**
     * Recuperer l'utilisateur par Id
     * @return utilisateur
     */
   public Optional<Utilisateur> recupererUtilisateurParId(Long id){
        return utilisateurService.findById(id);
   }

    /**
     * Creation d'un utilisateur
     * @return utilisateur
     */
   public Utilisateur creationUtilisateur(Utilisateur nouveauUtilisateur){
       return utilisateurService.save(nouveauUtilisateur);
   }

    /**
     * Suppression de l'id utilisateur
     * @param id utilisateur
     */
   public void suppressionUtilisateur(Long id){
       utilisateurService.deleteById(id);
   }

    /**
     * Mettre à jour un utilisateur
     * @param id utilisateur
     * @param utilisateur utilisateur
     * @return utilisateur
     */
   public Utilisateur miseAjourUtilisateur(Long id , Utilisateur utilisateur){
       return  utilisateurService.save(utilisateur);
   }
}
