package com.blog_app.blog_app_apis.config;

import com.blog_app.blog_app_apis.security.CustomUserDetailService;
import com.blog_app.blog_app_apis.security.JwtAuthenticationEntryPoint;
import com.blog_app.blog_app_apis.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomUserDetailService userDetailsService;

    public SecurityConfig(CustomUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    public static final String[] PUBLIC_URLS={
            "/api/v1/auth/**",
            "/v3/api-docs",
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**"
    };

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll() // anyone can GET
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e.authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
