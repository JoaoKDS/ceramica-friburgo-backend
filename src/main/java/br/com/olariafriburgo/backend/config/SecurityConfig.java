package br.com.olariafriburgo.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    // Sites autorizados a chamar esta API, separados por vírgula.
    // O valor vem do application.properties (e, em produção, da variável CORS_ORIGINS).
    @Value("${app.cors.allowed-origins}")
    private String origensPermitidas;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Pré-verificação que o navegador faz antes de chamar a API de outro site
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // O catálogo continua público: qualquer visitante pode LER os produtos
                        .requestMatchers(HttpMethod.GET, "/produtos", "/produtos/**").permitAll()
                        // Criar, alterar e apagar produtos exige usuário e senha
                        .anyRequest().authenticated()
                )
                // Login simples por usuário e senha (é o que o Postman usa em "Basic Auth")
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        List<String> origens = Arrays.stream(origensPermitidas.split(","))
                .map(String::trim)
                .filter(origem -> !origem.isEmpty())
                .toList();

        config.setAllowedOrigins(origens);
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}