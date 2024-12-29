package com.example.kanbansystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService())
            .passwordEncoder(getPasswordEncoder()); // Add password encoder here
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/authenticate").permitAll()
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers(HttpMethod.GET, "/Allusers").permitAll()
                .antMatchers(HttpMethod.GET, "/users/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/boards/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/boardsId/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/boards").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/boardsDeleted/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/tasks/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/tasksId/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/tasks").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/tasks/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/tasksDeleted/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/sprints/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/sprintId/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/sprints").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/sprintDeleted/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Bean
    public UserDetailsService myUserDetailsService() {
        return new MyuserDetailsService();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCryptPasswordEncoder
    }
}
