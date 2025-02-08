package com.limitlessmoviesapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/WEB-INF/**", "/webjars/**", "/css/**", "/images/**",
                                        "/login", "/register").permitAll()
                                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                .anyRequest().authenticated())
                .formLogin(form ->
                        form.loginPage("/login")
                                .usernameParameter("email") // For login will be required email not a username
                                .permitAll())
                .logout(logout ->
                        logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authenticationProvider(authenticationProvider);

        return http.build();
    }
}
