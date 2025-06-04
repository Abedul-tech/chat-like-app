package com.alen.auth.service;

import com.alen.auth.dto.LoginDto;
import com.alen.auth.dto.RegisterDto;
import com.alen.auth.dto.TokenDto;
import com.alen.auth.jwt.JwtService;
import com.alen.auth.model.Gender;
import com.alen.auth.model.Role;
import com.alen.auth.model.User;
import com.alen.auth.repository.RoleRepository;
import com.alen.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AuthenticationManager authManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    public TokenDto login(LoginDto user){
        try{
            authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            // Generate a token if authentication is successful
            String token = jwtService.generateToken(userDetails);
            return TokenDto
                    .builder()
                    .token(token)
                    .build();
        }catch ( AuthenticationException e){
            // Handle authentication failure
            System.out.println("Authentication failed: " + e.getMessage());
            return TokenDto
                    .builder()
                    .token("Authentication failed")
                    .build();
        }

    }

    public TokenDto register(RegisterDto user) {
        Role role=roleRepository.findById(user.getId_role()).orElseThrow(()->new RuntimeException("Role not Found"));
        User userModel=User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .gender(Gender.MALE)
                .phoneNumber(user.getPhoneNumber())
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .lastLogin(LocalDateTime.now())
                .build();
        userModel.addRole(role);
        userRepository.save(userModel);
        /*Token generation*/
        String token = jwtService.generateToken(userModel);
        return TokenDto
                .builder()
                .token(token)
                .build();
    }
    public boolean validateToken(TokenDto tokenDto){//Use class "Boolean" when you want to return null as well
        String token = tokenDto.getToken();
        if(token == null){
            return false;
        }
        String username = jwtService.getSubject(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtService.isTokenValid(userDetails,token);
    }
}
