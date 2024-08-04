package com.spring_security_jwt.service;

import com.spring_security_jwt.dto.LoginRequest;
import com.spring_security_jwt.dto.LoginResponse;
import com.spring_security_jwt.entity.Role;
import com.spring_security_jwt.entity.User;
import com.spring_security_jwt.repo.UserRepo;
import com.spring_security_jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findById(username).get();

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getUserPassword(),
                    getAuthority(user)
                 );
            }else{
        throw new UsernameNotFoundException("User not found with username: "+username);
            }
        }

        // meken authorities tika ganna eka karanne
    private Set getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//        for(Role role : user.getRole()){
//            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
//        }

        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+ role.getRoleName()));
        });
        return authorities;
    }

    public LoginResponse createJwtToken(LoginRequest loginRequest) throws Exception{
        String username = loginRequest.getUsername();
        String userPassword = loginRequest.getUserPassword();

        authenticate(username, userPassword);
        //loadUserByUsername ekta send karala balanawa me username eken user kenek inawada saha authorities tika set karanawa
        UserDetails userDetails = loadUserByUsername(username);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);
        User user = userRepo.findById(username).get();

        LoginResponse loginResponse = new LoginResponse(
                user,
                newGeneratedToken
        );

        return loginResponse;
    }

    private void authenticate(String username, String userPassword) throws Exception{
        try {
            //Spring security wala thiyana authenticate mthod eka
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,userPassword));
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS",e);
        }
    }
}
