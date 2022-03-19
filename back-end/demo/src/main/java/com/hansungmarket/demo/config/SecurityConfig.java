package com.hansungmarket.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()

                .and()
                .csrf().disable() // 테스트용, 나중에 설정해야 함
                .authorizeRequests()
                .antMatchers("/main").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()

                .antMatchers(HttpMethod.GET, "/api/boards/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/boards").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/api/boards/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/api/boards/**").hasRole("USER")

                .antMatchers(HttpMethod.GET, "/api/images/**").permitAll()

                .antMatchers(HttpMethod.GET, "/api/users").authenticated()

                .antMatchers(HttpMethod.POST, "/api/signUp/**").permitAll()

                .antMatchers(HttpMethod.GET, "/api/auth/mail").authenticated()
                .antMatchers(HttpMethod.GET, "/api/auth/{token}").permitAll()

                .anyRequest().authenticated()

                .and()
                .formLogin()
//                .loginPage("/login").permitAll()

                .and()
                .logout().permitAll();
    }

}

