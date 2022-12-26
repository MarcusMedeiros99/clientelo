package br.com.alura.clientelo.web.config.security;

import br.com.alura.clientelo.web.core.usecase.auth.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Profile("prod")
public class WebSecurityConfig {

    @Autowired
    UsuarioService usuarioService;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorize -> authorize
                        .regexMatchers(HttpMethod.GET, "/api/categorias")
                        .permitAll()
                        .regexMatchers(HttpMethod.GET, "/api/produtos")
                        .permitAll()
                        .regexMatchers(HttpMethod.POST, "/api/auth/signup")
                        .permitAll()
                        .regexMatchers(HttpMethod.POST, "/api/auth/signin")
                        .permitAll()
                        .regexMatchers(HttpMethod.POST, "/api/clientes")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
