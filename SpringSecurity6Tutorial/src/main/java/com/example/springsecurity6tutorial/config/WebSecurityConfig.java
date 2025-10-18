package com.example.springsecurity6tutorial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Configuration //add the @Configuration because it is a config class
@EnableWebSecurity // imports a bunch of security classes such as WebSecurityConfiguration.class, SpringWebMvcImportSelector.class, OAuth2ImportSelector.class,
public class WebSecurityConfig {



    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean // here we use SecurityFilterChain
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { //httpSecurity is a builder (blueprint) that defines all our filters, and in what order they should be played
        // if this class is left empty, we practically turn off spring security -> no filters!
        httpSecurity
                .csrf(csrf -> csrf.disable()) //disabling csrf (not good in production)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("register", "login").permitAll()                // whitelist this url so that you do not need to be logged in
                        .requestMatchers("/api/**").hasAnyRole("USER", "ADMIN")   // permit only logged in users with roles User and Admin
                        .requestMatchers("/admin/**").hasRole("ADMIN")                  // permit only logged in Admin
                        .anyRequest().authenticated() // here we use the filter authorizeHttpRequests. We tell the filter to authenticate all http requests, as long as you are logged in
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/login") // Spring handles POST /login. Guest users will always be redirected to this url.
                        .permitAll()
                )
                //.formLogin(Customizer.withDefaults()) // here we give the user the basic prebuilt login form to login
                .httpBasic(Customizer.withDefaults()); // here we give user an ugly popup to allow them to login/authenticate themselves

        return httpSecurity.build(); // here we build up our filters
    }

    //@Bean
    /* old code -> in memory users (no db)
    public UserDetailsService userDetailsService(){ // takes user as the input and gives user data back

        // since we are currently not using a db, we will use InMemoryUserDetailsManager's built in User class methods.
        UserDetails user1
                = User.withUsername("user1")
                .password("{noop}123") // since we are currently sending passwords as PlainText, we will get an error (noop fixes this)
                .roles("USER")
                .build();  // now we have used a built a new user!

        UserDetails user2
                = User.withUsername("user2")
                .password("321")
                .roles("USER")
                .build();  // now we have used a built a new user!


        return new InMemoryUserDetailsManager(user1, user2);
    }

     */


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(14);
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        //provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }


}
