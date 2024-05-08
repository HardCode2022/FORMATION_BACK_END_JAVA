package com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.securityConfig;

public class JwtAuthentificationResponse {

     private String token ;

    public JwtAuthentificationResponse(String token) {
        this.token=token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
