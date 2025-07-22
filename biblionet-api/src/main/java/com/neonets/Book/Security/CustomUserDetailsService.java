package com.neonets.Book.Security;

import com.neonets.Book.user.User;
import com.neonets.Book.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try
        {
            User user = userRepository.findByEmail(username);
            return new CustomUserDetails(user);
        }
        catch (UsernameNotFoundException ex)
        {
            throw new UsernameNotFoundException("Username not found! {}");
        }

    }
}
