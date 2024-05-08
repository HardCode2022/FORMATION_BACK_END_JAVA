package com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.service;

import org.springframework.security.core.GrantedAuthority;

public class UserCustomAutority implements GrantedAuthority {

    private String autority;

    public UserCustomAutority(String autority) {
        this.autority = autority;
    }

    @Override
    public String getAuthority() {
        return autority;
    }
}
