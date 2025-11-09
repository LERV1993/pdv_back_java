package com.pdv.project.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pdv.project.dto.request.LoginRequestDTO;
import com.pdv.project.dto.response.AuthResponseDTO;
import com.pdv.project.entity.AuthCredentialsEntity;
import com.pdv.project.enums.Role;
import com.pdv.project.exceptions.AuthCrendentialsNotFoundExeption;
import com.pdv.project.repository.AuthCredentialsRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthCredentialsRepository authCredentialsRepository;
    private final JwtService jwtService;

    public UserDetailsServiceImpl(AuthCredentialsRepository authCredentialsRepository,
            JwtService jwtService) {
        this.authCredentialsRepository = authCredentialsRepository;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return findUserByUserName(username);
        } catch (AuthCrendentialsNotFoundExeption e) {
            log.error("User not found: {}", username, e.getMessage());
            throw new UsernameNotFoundException("Usuario no encontrado: " + username, e);
        }
    }

    public UserDetails findUserByUserName(String email) throws AuthCrendentialsNotFoundExeption {

        if (email.isBlank()) {
            throw new AuthCrendentialsNotFoundExeption("email cannot be empty");
        }

        AuthCredentialsEntity authCredentials = authCredentialsRepository.findByEmail(email).get();

        if (authCredentials == null) {
            log.warn("Auth credentials not found for email: {}", email);
            throw new AuthCrendentialsNotFoundExeption("Auth credentials not found");
        }

        return buildUserDetails(authCredentials);
    }

    private UserDetails buildUserDetails(AuthCredentialsEntity authCredentials) {
        return User.builder()
                .username(authCredentials.getEmail())
                .password(authCredentials.getPassword())
                .disabled(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .accountLocked(false)
                .authorities(convertToAuthorities(authCredentials.getRole()))
                .build();
    }

    public AuthResponseDTO authenticateUser(LoginRequestDTO request) {

        log.info("user: {} - pass: {}",request.getUsername() , request.getPassword() );

        if (request == null || request.getUsername() == null || request.getPassword() == null
                || request.getUsername().isBlank() || request.getPassword().isBlank()) {
            throw new BadCredentialsException("Username and password cannot be empty");
        }

        UserDetails userDetails = loadUserByUsername(request.getUsername());

        if (!AuthPassword.matches(request.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        Authentication authentication = createAuthentication(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("email", request.getUsername());

        String accessToken = jwtService.generateToken(extraClaims,userDetails);

        return AuthResponseDTO.builder()
                .username(userDetails.getUsername())
                .message("Authentication successfull")
                .jwt(accessToken)
                .build();
    }

    private Authentication createAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
    }

    public Collection<? extends GrantedAuthority> convertToAuthorities(Role role) {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }
}