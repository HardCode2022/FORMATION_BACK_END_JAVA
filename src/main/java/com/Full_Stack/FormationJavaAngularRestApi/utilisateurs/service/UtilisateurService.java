package com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.service;

import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.entite.Utilisateur;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    /**
     * Recuperation de l'ensemble des utilisateurs
     * @return list utilisateurs
     */
    public List<Utilisateur> recupererListeUtilisateur(){
        return (List<Utilisateur>) utilisateurRepository.findAll();
    }

    /**
     * Recuperer l'utilisateur par Id
     * @return utilisateur
     */
   public Optional<Utilisateur> recupererUtilisateurParId(Long id){
        return utilisateurRepository.findById(id);
   }

    /**
     * Creation d'un utilisateur
     * @return utilisateur
     */
   public Utilisateur creationUtilisateur(Utilisateur nouveauUtilisateur){
       return utilisateurRepository.save(nouveauUtilisateur);
   }

    /**
     * Suppression de l'id utilisateur
     * @param id utilisateur
     */
   public void suppressionUtilisateur(Long id){
       utilisateurRepository.deleteById(id);
   }

    /**
     * Mettre à jour un utilisateur
     * @param id utilisateur
     * @param utilisateur utilisateur
     * @return utilisateur
     */
   public Utilisateur miseAjourUtilisateur(Long id , Utilisateur utilisateur){
       return  utilisateurRepository.save(utilisateur);
   }
}
