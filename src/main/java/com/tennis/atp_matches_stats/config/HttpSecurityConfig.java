package com.tennis.atp_matches_stats.config;

import com.tennis.atp_matches_stats.util.UserRoles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;


@Configuration
public class HttpSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
        return http
                .requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure().and()
                .cors().and()
                .csrf().ignoringRequestMatchers("/api/**").and()
                .authorizeHttpRequests()
                    .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                    .anyRequest().hasRole(UserRoles.USER).and()
                .exceptionHandling().authenticationEntryPoint(
                    new LoginUrlAuthenticationEntryPoint("/login?loginRequired=true")).and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("email")
                    .failureUrl("/login?loginError=true").and()
                .logout()
                    .logoutSuccessUrl("/login?logoutSuccess=true")
                    .deleteCookies("JSESSIONID").and()
                .build();
    }

}
