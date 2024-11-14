package com.calvarez.carregistry.config;


import com.calvarez.carregistry.filter.JwtAuthenticationFilter;
import com.calvarez.carregistry.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private static final String CAR_ENDPOINT ="/car/**";
    private static final String BRAND_ENDPOINT ="/brand/**";
    private static final String ADMIN_ROLE ="ROLE_VENDOR";


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests( authorize -> authorize
                .requestMatchers(HttpMethod.POST, "/user/**","/user/login", "*/signup").permitAll()
                .requestMatchers(HttpMethod.GET, "/test/**").permitAll()
                .requestMatchers(HttpMethod.GET, CAR_ENDPOINT).hasAnyAuthority("ROLE_CLIENT", ADMIN_ROLE)
                .requestMatchers(HttpMethod.POST, CAR_ENDPOINT).hasAnyAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.PUT, CAR_ENDPOINT).hasAnyAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.DELETE, CAR_ENDPOINT).hasAnyAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.GET, BRAND_ENDPOINT).hasAnyAuthority("ROLE_CLIENT", ADMIN_ROLE)
                .requestMatchers(HttpMethod.POST, BRAND_ENDPOINT).hasAnyAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.PUT, BRAND_ENDPOINT).hasAnyAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.DELETE, BRAND_ENDPOINT).hasAnyAuthority(ADMIN_ROLE)
                .anyRequest().authenticated())
        .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
