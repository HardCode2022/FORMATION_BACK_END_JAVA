package com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.exception;

public class PasUtilisateurException extends Exception {

	public PasUtilisateurException(String string) {
		super(string);
	}
	
	public PasUtilisateurException(String string,Throwable id ) {
		super(string,id );
	}

	private static final long serialVersionUID = 302956740829596387L;

	
}
