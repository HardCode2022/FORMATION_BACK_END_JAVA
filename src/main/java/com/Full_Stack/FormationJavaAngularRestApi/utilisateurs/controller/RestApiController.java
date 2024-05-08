/**
 * 
 */
package com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.controller;


import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.entite.User;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.entite.Utilisateur;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.exception.PasUtilisateurException;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.modelForm.LoginForm;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.repository.UserRepository;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.securityConfig.JwtAuthentificationResponse;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.securityConfig.JwtTokenProvider;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.service.CustomUserDetailsService;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author Maurice G
 *
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RestApiController {

	private static Logger LOGGER = Logger.getLogger(RestApiController.class.getName());
    @Autowired
	private UtilisateurService utilisateurService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private JwtTokenProvider jwtTokenProvider ;
	private static final List<String>listRoles= List.of("CREATION","CONSULTATION","MODIFICATION","ADMIN","SUPPRESSION");
	@PostMapping("/auth/login")
	public ResponseEntity<?> authUser (@RequestBody LoginForm loginFormRequest){
		//Rechercher un utilisateur en fonction de son username
		User user = userRepository.findUserByName(loginFormRequest.getUsername());

		if (null!=user && isUser(loginFormRequest, user)) {
			UserDetails userDetails =customUserDetailsService.loadUserByUsername(loginFormRequest.getUsername());
			if (userDetails != null && userDetails.getAuthorities().stream().anyMatch(autority-> listRoles.contains(autority.getAuthority()))) {
				Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String token =jwtTokenProvider.generateToken(authentication);

				return  ResponseEntity.ok(new JwtAuthentificationResponse(token));
			}else{
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("L'utilisateur n'a pas tous les droits necessaire");
			}
		}else{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nom utilisateur et mot de passe Incorrect");
		}
	}

	private static boolean isUser(LoginForm loginFormRequest, User user) {
		return user.getUsername().equals(loginFormRequest.getUsername()) && user.getPassword().equals(loginFormRequest.getPassword());
	}

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
