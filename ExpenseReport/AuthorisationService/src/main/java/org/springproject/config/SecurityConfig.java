package org.springproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder){

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("Test@123"))
                .roles("user")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("Admin@123")
                .roles("admin")
                .build();

       return new  InMemoryUserDetailsManager(user, admin);

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

            return http
                    .csrf(csrf-> csrf.disable())
                    .securityMatcher("/api/**")
                    .authorizeHttpRequests(request -> request.anyRequest().hasAnyRole("user","admin"))
                    .httpBasic(Customizer.withDefaults())
                    .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
