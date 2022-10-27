package com.Full_Stack.FormationJavaAngularRestApi;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.UtilisateurController;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.entite.Utilisateur;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.repoutilisateur.UtilisateurJpaRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.Assert;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class FormationJavaAngularRestApiApplicationTests {

	private UtilisateurJpaRepo utilisateurJpaRepo;

	private UtilisateurController utilisateurController;

	@Before
	public void init() {
		utilisateurController = new UtilisateurController();
		utilisateurJpaRepo = Mockito.mock(UtilisateurJpaRepo.class);
		utilisateurController.setUtilisateurJpaRepo(utilisateurJpaRepo);

	}

	@org.junit.Test
	public void recuperationUtilisateurTest() throws JsonProcessingException, Exception, IOException {

		ObjectMapper mapper = new ObjectMapper();

		File file = new File(
				"C:/Users/Ordinateur/Desktop/FORMATION FULL STACK/WORKSPACE_BACK_END/FormationJavaAngularRestApi/FormationJavaAngularRestApi/src/test/java/ressources/utilisateur.json");

		Utilisateur[] utilisateur = mapper.readValue(file, Utilisateur[].class);

		List<Utilisateur> utilir = utilisateurJpaRepo.findAll();
		Assert.assertEquals(utilir, utilisateur);

	}

}
