package com.ByteCraft.IotProject.Security;

import com.ByteCraft.IotProject.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


    @Service
    public class UserDetailService implements UserDetailsService {

        private final UserRepository userRepository;

        public UserDetailService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            var u = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

            return org.springframework.security.core.userdetails.User
                    .withUsername(u.getEmail())
                    .password(u.getPassword())     // DB’de şifre (ileride BCrypt olacak)
                    .roles(u.getRole().name())     // USER / ADMIN
                    .build();
        }
    }


