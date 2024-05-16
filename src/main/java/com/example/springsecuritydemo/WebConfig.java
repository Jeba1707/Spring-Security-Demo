package com.example.springsecuritydemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebConfig {

    @Autowired
    private UserDetailsService myUserDetailService;

@Bean
public AuthenticationProvider authenticationProvider(){
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(myUserDetailService);
    provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
    return provider;
}


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorizeReq -> authorizeReq.anyRequest().authenticated());
        http.formLogin(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public UserDetailsService  userDetailsService(){
        UserDetails user1 = User.withDefaultPasswordEncoder().username("user").password("password").roles("user").build();
        UserDetails user2 = User.withDefaultPasswordEncoder().username("jeba").password("123").roles("admin").build();
        return new InMemoryUserDetailsManager(user1, user2);
    }

}
