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
        Random random = new Random();
        // Si l'utilisateur n'est pas trouvé, créez un nouvel utilisateur avec des informations minimales
        if (user == null) {
            // Créez un nouvel utilisateur avec un rôle par défaut
            user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(""));
            // Créer un nouveau rôle ADMIN
            Role adminRole = new Role(random.nextLong(), "ADMIN");
            adminRole = roleRepository.save(adminRole);
            user.setRoles(List.of(adminRole));
            userRepository.save(user);
        }

         Optional<Role> roles = roleRepository.findById(user.getId());

        if (null == user.getRoles() && roles.isPresent()){
            Role role = roles.get();
            user.setRoles( new ArrayList<>());
            user.getRoles().add(role);
         }
        // Mapper les rôles de l'utilisateur en GrantedAuthority
        List<CustomGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new CustomGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        // Construisez UserDetails à partir de l'utilisateur trouvé ou créé
        return new org.springframework.security.core.userdetails.User(user.getUsername(), passwordEncoder.encode(user.getPassword()), authorities);
 }
}
