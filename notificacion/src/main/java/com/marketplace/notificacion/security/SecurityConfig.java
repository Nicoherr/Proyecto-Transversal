package com.marketplace.notificacion.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration // Indica que esta clase provee configuraciones para Spring
@EnableMethodSecurity // Habilita seguridad a nivel de métodos (ej: @PreAuthorize)
public class SecurityConfig {

    @Bean // Registra este método como un bean de Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // Configura la cadena de filtros de seguridad
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita protección CSRF (útil para APIs REST sin sesiones)
                .cors(Customizer.withDefaults()) // Habilita CORS con configuración por defecto
                .sessionManagement(session -> session // Configura gestión de sesiones
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sin estado (no guarda sesiones, ideal para JWT)
                .authorizeHttpRequests(auth -> auth // Configura reglas de autorización para peticiones HTTP
                        .requestMatchers("/auth/**").permitAll() // Las rutas raíz ("/", "/index", etc.) son públicas (permite todo)
                        .requestMatchers(HttpMethod.GET, "/api/v1/notificaciones/**").authenticated() // GET a notificaciones requiere autenticación
                        .requestMatchers("/api/v1/notificaciones/**").hasRole("USER") // Cualquier otro método HTTP sobre notificaciones requiere rol USER
                        .anyRequest().authenticated() // Cualquier otra petición requiere autenticación
                );
        return http.build(); // Construye y retorna la configuración
    }

    @Bean // Registra el codificador de contraseñas como bean
    public PasswordEncoder passwordEncoder() { // Provee un codificador de contraseñas
        return new BCryptPasswordEncoder(); // Usa BCrypt (hash seguro, sal automática)
    }
}

