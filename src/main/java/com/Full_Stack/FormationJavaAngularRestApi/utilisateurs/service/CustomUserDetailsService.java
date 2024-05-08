package com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.service;

import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.entite.Role;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.entite.User;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.repository.RoleRepository;
import com.Full_Stack.FormationJavaAngularRestApi.utilisateurs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //Rechercher un utilisateur en fonction de son username
        User user = userRepository.findUserByName(username);

        if(null== user){
            throw  new UsernameNotFoundException("L'utilisateur avec identifiant :" + username + "est absent dans le referentiel");
        }

        Optional<Role> roles = roleRepository.findById(user.getId());
        if(null==user.getRoles() && roles.isPresent()){
           Role role = roles.get();
           user.setRoles( new ArrayList<>());
           user.getRoles().add(role);
        }else{
            throw  new UsernameNotFoundException("Aucun role n'est associé à cet username : " + username );
        }

        List<UserCustomAutority> autorityList = user.getRoles().stream().map(role -> new UserCustomAutority(role.getName())).toList();

        return new  org.springframework.security.core.userdetails.User(user.getUsername(),passwordEncoder.encode(user.getPassword()),autorityList);
    }
}
