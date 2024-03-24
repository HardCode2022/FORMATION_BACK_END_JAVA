/**
 * 
 */
package com.Full_Stack.FormationJavaAngularRestApi.controller;


import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.entite.User;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.repository.UserRepository;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.securityConfig.JwtAuthenticationResponse;
import com.Full_Stack.FormationJavaAngularRestApi.modele.LoginForm;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.entite.Utilisateur;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.exception.PasUtilisateurException;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.securityConfig.JwtTokenProvider;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.service.CustomUserDetailsService;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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
//@RequestMapping("api/v1")
public class RestApiController {

	private static Logger LOGGER = Logger.getLogger(RestApiController.class.getName());

    @Autowired
	private UtilisateurService utilisateurService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder; // Votre encodeur de mot de passe
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/auth/login")
	public ResponseEntity<?> authUser(@RequestBody LoginForm loginRequest) {

		User user = userRepository.findByUsername(loginRequest.getUsername());
		// Vérifier si l'utilisateur existe et si le mot de passe est correct
		if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
			// Si les informations d'identification sont valides, générer le jeton JWT
			UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
			Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtTokenProvider.generateToken(authentication);
			return ResponseEntity.ok(new JwtAuthenticationResponse(token));
		} else {
			// Si les informations d'identification ne sont pas valides, renvoyer une erreur d'authentification
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nom d'utilisateur ou mot de passe incorrect");
		}
	}


	// Recuperation de la liste complete de utilisateurs
	@GetMapping("utilisateurs")
	public ResponseEntity<List<Utilisateur>> recupererListeUtilisateur() {
		LOGGER.info("Recuperation de la liste des utilisateurs");
		return new ResponseEntity<>(utilisateurService.recupererListeUtilisateur(), HttpStatus.OK);
	}

	// Recuperer un utilisateur par Id
	@GetMapping("/utilisateurs/{id}")
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
	public ResponseEntity<Utilisateur> creationUtilisateur(@RequestBody Utilisateur nouveauUtilisateur) throws ServerException {
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
