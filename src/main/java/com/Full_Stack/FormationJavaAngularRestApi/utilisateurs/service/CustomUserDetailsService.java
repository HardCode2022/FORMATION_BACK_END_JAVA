package com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.service;


import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.entite.Role;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.entite.User;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.repository.RoleRepository;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.repository.UserRepository;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.securityConfig.CustomGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // Recherche de l'utilisateur dans la base de données
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec le nom d'utilisateur: " + username);
        }
        Optional<Role> roles = roleRepository.findById(user.getId());
        if (null == user.getRoles() && roles.isPresent()){
            Role role = roles.get();
            user.setRoles( new ArrayList<>());
            user.getRoles().add(role);
        } else {
            throw new UsernameNotFoundException("Pas de de roles associés pour le Username : " + username);
        }
        // Mapper les rôles de l'utilisateur en GrantedAuthority
        List<CustomGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new CustomGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        // Construisez UserDetails à partir de l'utilisateur trouvé ou créé
        return new org.springframework.security.core.userdetails.User(user.getUsername(), passwordEncoder.encode(user.getPassword()), authorities);
 }
}
