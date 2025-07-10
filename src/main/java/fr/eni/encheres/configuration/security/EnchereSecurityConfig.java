package fr.eni.encheres.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class EnchereSecurityConfig {

    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    public EnchereSecurityConfig(CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
    }
    private static final String SELECT_USER = """
            SELECT pseudo, mot_de_passe, active FROM UTILISATEURS WHERE pseudo = ?
            """;
    private static final String SELECT_ROLES = """
            SELECT u.pseudo, r.role FROM UTILISATEURS u
            JOIN ROLES r ON r.IS_ADMIN = u.administrateur
            WHERE pseudo = ?
            """;

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcDetailsManager.setUsersByUsernameQuery(SELECT_USER);
        jdbcDetailsManager.setAuthoritiesByUsernameQuery(SELECT_ROLES);
        return jdbcDetailsManager;
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // <----- temporaire
                .authorizeHttpRequests(auth -> {
            auth
//                    .requestMatchers(HttpMethod.GET,"/profil/*").authenticated()
//                    .requestMatchers(HttpMethod.POST,"/profil/*").authenticated()
                    .requestMatchers(HttpMethod.GET, "/credit").authenticated()
                    .requestMatchers(HttpMethod.GET,"/signin").permitAll()
                    .requestMatchers(HttpMethod.POST,"/signin").permitAll()
                    .requestMatchers("/*").permitAll()
                    .requestMatchers("/css/*").permitAll()
                    .requestMatchers("/font/*").permitAll()
                    .requestMatchers("/img/*").permitAll()
                    .requestMatchers("/.well-known/**").permitAll()  // Permet l'accès à /well-known
                    .anyRequest().permitAll();
        });

        http.formLogin(form -> {
            form.loginPage("/login").permitAll();
            form.failureHandler(customAuthenticationFailureHandler);
            form.usernameParameter("pseudo");
            form.passwordParameter("motDePasse");
            form.defaultSuccessUrl("/session").permitAll();
        });

        http.logout(logout -> logout
                .logoutRequestMatcher(request ->
                        request.getMethod().equals("GET") && request.getRequestURI().equals("/logout")
                )
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/")
                .permitAll()
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
