package com.hansungmarket.demo.config;

import com.hansungmarket.demo.config.login.CustomAuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CustomAuthenticationHandler customAuthenticationHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()

                .and()
                .csrf().disable()// 테스트용, 나중에 설정해야 함

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
                .antMatchers(HttpMethod.GET, "/api/signUp/**").permitAll()

                .antMatchers("/api/login/fail").permitAll()
                .antMatchers("/api/logout/success").permitAll()

                .antMatchers(HttpMethod.GET, "/api/auth/mail").authenticated()
                .antMatchers(HttpMethod.GET, "/api/auth/{token}").permitAll()

                .anyRequest().authenticated()

                .and()
                .formLogin()
                    .loginPage("/api/login")
                    .loginProcessingUrl("/api/login").permitAll() // post 요청
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(customAuthenticationHandler)
                    .failureHandler(customAuthenticationHandler)

                .and()
                .logout()
                    .logoutUrl("/api/logout") // csrf 적용 시, post 요청
                    .logoutSuccessHandler(customAuthenticationHandler);
    }

}

