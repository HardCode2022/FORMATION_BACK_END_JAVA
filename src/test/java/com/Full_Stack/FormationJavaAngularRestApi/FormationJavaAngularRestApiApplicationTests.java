package com.Full_Stack.FormationJavaAngularRestApi;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.service.UtilisateurService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.entite.Utilisateur;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.Assert;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class FormationJavaAngularRestApiApplicationTests {

	private UtilisateurService utilisateurService;

	@Before
	public void init() {
		utilisateurService = Mockito.mock(UtilisateurService.class);
		//utilisateurController.setUtilisateurJpaRepo(utilisateurJpaRepo);

	}

	@org.junit.Test
	public void recuperationUtilisateurTest() throws JsonProcessingException, Exception, IOException {

		ObjectMapper mapper = new ObjectMapper();

		File file = new File(
				"C:/Users/Ordinateur/Desktop/FORMATION FULL STACK/WORKSPACE_BACK_END/FormationJavaAngularRestApi/FormationJavaAngularRestApi/src/test/java/ressources/utilisateur.json");

		Utilisateur[] utilisateur = mapper.readValue(file, Utilisateur[].class);

		List<Utilisateur> utilir = utilisateurService.recupererListeUtilisateur();
		Assert.assertEquals(utilir, utilisateur);

	}
}
