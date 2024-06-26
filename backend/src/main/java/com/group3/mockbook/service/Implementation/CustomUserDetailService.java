package com.group3.mockbook.service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.group3.mockbook.model.User;
import com.group3.mockbook.repository.UserRepository;
import com.group3.mockbook.security.UserPrincipal;

/**
 * CustomUserDetailService is a class that implements the UserDetailsService interface.
 * It is used to load user-specific data when a user logs in. 
 * It is used by the AuthenticationManager to authenticate a user.
 */
@Service("userDetailsService")
public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    /**
     * This method is used to load user-specific data when a user logs in.
     * It is used by the AuthenticationManager to authenticate a user.
     * @param email The email of the user
     * @return UserDetails The user-specific data
     * @throws UsernameNotFoundException If the user is not found
     */
    @Override
    public UserPrincipal loadUserByUsername(String email) throws UsernameNotFoundException {
        // Implementation goes here
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email "+email);
        }
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setId(user.get().getId());
        userPrincipal.setUsername(user.get().getEmail());
        userPrincipal.setPassword(user.get().getPassword());
        userPrincipal.setEnabled(user.get().isEnabled());
        userPrincipal.setAuthorities(List.of(new SimpleGrantedAuthority(user.get().getRole())));
        return userPrincipal;
    }
    
}
