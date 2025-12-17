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
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;

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
                // CSRF kapalı (JWT + fetch için doğru)
                .csrf(AbstractHttpConfigurer::disable)

                // Default Spring Security login UI kapalı
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // JWT => stateless
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Yetkilendirme kuralları
                .authorizeHttpRequests(auth -> auth
                        // ✅ static (PathRequest)
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()

                        // ✅ fallback: root seviyedeki dosyaların (senin yapında var)
                        .requestMatchers("/", "/index.html", "/dashboard.html", "/styles.css", "/app.js").permitAll()
                        .requestMatchers("/img/**", "/css/**", "/js/**", "/assets/**", "/favicon.ico").permitAll()

                        // ✅ auth
                        .requestMatchers("/api/auth/**").permitAll()

                        .anyRequest().authenticated()
                )

                // JWT Filter
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
