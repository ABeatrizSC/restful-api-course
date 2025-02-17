package com.example.api.services;

import com.example.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServices implements UserDetailsService {

    private Logger logger = Logger.getLogger(UserServices.class.getName());

    @Autowired
    UserRepository repository;

    //Torna-se required
    public UserServices(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Finding one user by name " + username + "!");
        var user = repository.findByUsername(username);
        if (user != null){
            return new User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities() // metodo que retorna as permissões (roles)
            );
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
    }
}
