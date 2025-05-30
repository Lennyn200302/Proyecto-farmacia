package com.mycompany.security;

import com.mycompany.modelo.Usuario;
import com.mycompany.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)    //Habilita las anotaciones como @PreAuthorize, etc
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final UsuarioRepository usuarioRepo;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter, UsuarioRepository usuarioRepo) {
        this.jwtFilter = jwtFilter;
        this.usuarioRepo = usuarioRepo;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Usuario u = usuarioRepo.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            return User.builder()
                    .username(u.getUsername())
                    .password(u.getPassword())
                    .roles(u.getRoles().stream().map(Enum::name).toArray(String[]::new))
                    .build();
        };
    }

    @Bean   //encriptacion del password
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/producto/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/categoria/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

