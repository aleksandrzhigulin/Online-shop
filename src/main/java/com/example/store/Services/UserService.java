package com.example.store.Services;

import com.example.store.Models.User;
import com.example.store.Repositories.UserRepository;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User getAuthorizedUser() {
       User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       System.out.println("Principal id: " + user.getId());
       return user;
    }
}
