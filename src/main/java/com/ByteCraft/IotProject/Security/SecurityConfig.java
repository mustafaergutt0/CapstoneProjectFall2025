package com.ByteCraft.IotProject.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF (şimdilik kapalı: fetch ile rahat POST + JWT stateless)
                .csrf(AbstractHttpConfigurer::disable)

                // Default login UI kapalı (Please sign in çıkmasın)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // JWT => stateless
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Endpoint policy (kurumsal erişim kuralları)
                .authorizeHttpRequests(auth -> auth
                        // UI statik dosyaları
                        .requestMatchers("/", "/index.html", "/app.js", "/styles.css").permitAll()
                        // Auth endpointleri public
                        .requestMatchers("/api/auth/**").permitAll()
                        // Diğer her şey token ister
                        .anyRequest().authenticated()
                )

                // JWT Filter'ı zincire ekle (Authorization: Bearer ... okunsun)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
