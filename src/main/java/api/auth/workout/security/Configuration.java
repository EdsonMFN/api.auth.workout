package api.auth.workout.security;


import api.auth.workout.service.AutorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@org.springframework.context.annotation.Configuration
@EnableWebSecurity
public class Configuration {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private AutorizationService autorizacaoService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(csrf-> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests( autorization -> autorization
                        .requestMatchers(HttpMethod.POST,"/acesso/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/acesso/cadastro").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.GET,"/acesso").permitAll()
                        .requestMatchers(HttpMethod.GET,"/acesso/authUsuario").permitAll()

                        .requestMatchers(antMatcher("/endereco/**")).hasRole("GESTOR")

                        .requestMatchers(antMatcher("/academia/**")).hasRole("GESTOR")
                        .requestMatchers(HttpMethod.GET,"/academia/**").hasAnyRole("GESTOR","ALUNO")

                        .requestMatchers(antMatcher("/professor/**")).hasRole("GESTOR")

                        .requestMatchers(antMatcher("/aluno/**")).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/aluno/academia/**").hasAnyRole("ADMIN","PROFESSOR")
                        .requestMatchers(HttpMethod.GET,"/aluno/**").hasAnyRole("ADMIN","PROFESSOR")

                        .requestMatchers(antMatcher("/personal/**")).hasRole("ADMIN")

                        .requestMatchers(antMatcher("/fichaDeTreino/**")).hasRole("PROFESSOR")
                        .requestMatchers(HttpMethod.GET,"/fichaDeTreino").hasAnyRole("PROFESSOR")
                        .requestMatchers(HttpMethod.GET,"/fichaDeTreino/aluno").hasAnyRole("PROFESSOR","ALUNO")
                        .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public BCryptPasswordEncoder cryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(autorizacaoService);
        authProvider.setPasswordEncoder(cryptPasswordEncoder());
        return authProvider;
    }

}
