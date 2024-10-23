package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${app.base-url}")
    private String baseUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/iduy/login", "/iduy/user-info").permitAll() // Permitir acceso sin autenticación
                                .requestMatchers("/peripheral-nodes/crear").permitAll() // Permitir acceso al endpoint /peripheral-nodes/crear sin autenticación
                                .anyRequest().authenticated() // Cualquier otra solicitud requiere autenticación
                )
                .csrf().disable() // Deshabilitar CSRF para pruebas (puedes habilitarlo después)
                .formLogin().disable() // Deshabilitar el formulario de inicio de sesión para pruebas (opcional)
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/") // URL a la que se redirige tras cerrar sesión
                                .invalidateHttpSession(true) // Invalidar la sesión
                                .clearAuthentication(true) // Limpiar la autenticación
                                .deleteCookies("JSESSIONID") // Eliminar las cookies de sesión
                );

        return http.build();
    }
}
