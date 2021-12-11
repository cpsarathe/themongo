package com.cp.bootmongo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${bootmongo.api.user}")
    private String user;

    @Value("${bootmongo.api.password}")
    private String password;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication().withUser(user).password(encoder.encode(password)).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/bootmango/**").hasAnyRole("USER")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf()
                .disable();
    }

}