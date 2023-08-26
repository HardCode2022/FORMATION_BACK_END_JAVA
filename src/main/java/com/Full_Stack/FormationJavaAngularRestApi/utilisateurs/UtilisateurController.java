/**
 * 
 */
package com.Full_Stack.FormationJavaAngularRestApi.utilisateurs;

import java.net.URI;
import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.entite.Utilisateur;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.exception.PasUtilisateurException;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.repoutilisateur.UtilisateurJpaRepo;

/**
 * @author Maurice
 *
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UtilisateurController {

	private static Logger LOGGER = Logger.getLogger(UtilisateurController.class.getName());

    @Autowired
	private UtilisateurService utilisateurService;

	// Recuperation de la liste complete de utilisateurs
	@GetMapping("utilisateurs")
	public ResponseEntity<List<Utilisateur>> recupererListeUtilisateur() {
		LOGGER.info("Recuperation de la liste des utilisateurs");
		return new ResponseEntity<>(utilisateurService.recupererListeUtilisateur(), HttpStatus.OK);
	}

	// Recuperer un utilisateur par Id
	@GetMapping("utilisateurs/{id}")
	public ResponseEntity<Utilisateur> recupererUtilisateurParId(@PathVariable Long id) throws PasUtilisateurException {
		Optional<Utilisateur> utilisateur = utilisateurService.recupererUtilisateurParId(id);
		if (utilisateur.isPresent()) {
			LOGGER.info("Recuperation utilisateur par id");
			return new ResponseEntity<>(utilisateur.get(), HttpStatus.OK);
		} else {
			throw new PasUtilisateurException("Aucun utilisateur trouvé pour l'id defini");
		}
	}

	// Publier/Sauvegarde des données en BDD
	@PostMapping("utilisateurs/creation")
	public ResponseEntity<Utilisateur> creationUtilisateur(@RequestBody Utilisateur nouveauUtilisateur)
			throws ServerException {

		Utilisateur utilisateur = utilisateurService.creationUtilisateur(nouveauUtilisateur);

		if (utilisateur == null) {
			throw new ServerException("Erreur de serveur");
		} else {

			URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(utilisateur.getId())
					.toUri();

			return ResponseEntity.created(url).build();
		}
	}

	// Suppression de l'utilisateur par Id
	@DeleteMapping("utilisateur/suppression/{id}")
	public ResponseEntity<Void> suppressionUtilisateurParId(@PathVariable Long id) {
		   utilisateurService.suppressionUtilisateur(id);
		return ResponseEntity.noContent().build();
	}

	// Mise à jour de l'utilisateur
	@PutMapping("utilisateur/miseAjour/{id}")
	public ResponseEntity<Utilisateur> miseAjourUtilisateur(@PathVariable Long id, @RequestBody Utilisateur util) {

		Utilisateur utilisateur = utilisateurService.miseAjourUtilisateur(id,util);

		return new ResponseEntity<Utilisateur>(utilisateur, HttpStatus.OK);

	}

}
