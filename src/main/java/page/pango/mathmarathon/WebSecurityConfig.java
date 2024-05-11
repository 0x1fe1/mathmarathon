package page.pango.mathmarathon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/mathmarathon", "/mathmarathon/settings",
                    "/mathmarathon/game", "/mathmarathon/game/results",
                    "/mathmarathon/rankings").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/signin")
                .loginProcessingUrl("/signin")
                .permitAll()
            )
            .logout(LogoutConfigurer::permitAll)
            .oauth2Login(Customizer.withDefaults());

        return http.build();
    }
}
