package com.example.api.config;

import com.example.api.security.jwt.JwtTokenFilter;
import com.example.api.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity // Habilita a segurança web no projeto
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Bean
    PasswordEncoder passwordEncoder() {
        // Cria um mapeamento de codificadores de senha
        Map<String, PasswordEncoder> encoders = new HashMap<>();

        // Configura o codificador PBKDF2 com os parâmetros personalizados
        Pbkdf2PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder("", 8, 185000,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        encoders.put("pbkdf2", pbkdf2Encoder); // Adiciona o codificador PBKDF2 ao mapa de encoders

        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder); // Define PBKDF2 como o padrão
        return passwordEncoder; // Retorna o codificador de senha
    }

    @Bean
    AuthenticationManager authenticationManagerBean(
            AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtTokenFilter customFilter = new JwtTokenFilter(tokenProvider);

        //@formatter:off
        return http
                .httpBasic(basic -> basic.disable()) // Desabilita a autenticação básica HTTP
                .csrf(csrf -> csrf.disable()) // Desabilita a proteção contra CSRF (não necessária para APIs stateless)
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona o filtro JWT antes do filtro de autenticação por nome/usuário e senha
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define a política de criação de sessão como stateless (não mantém sessão do usuário)
                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers(
                                        "/auth/signin",
                                        "/auth/refresh/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**"
                                ).permitAll() // Permite essas rotas sem autenticação
                                .requestMatchers("/api/**").authenticated() // Exige autenticação para qualquer rota começando com "/api"
                                .requestMatchers("/users").denyAll() // Bloqueia completamente o acesso à rota "/users"
                )
                .cors(cors -> {}) // Habilita suporte para CORS
                .build(); // Constrói a cadeia de filtros de segurança
        //@formatter:on
    }
}
