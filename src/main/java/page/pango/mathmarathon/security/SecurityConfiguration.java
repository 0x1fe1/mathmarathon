package page.pango.mathmarathon.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(registry -> registry
                .requestMatchers("/static/**", "/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
                .requestMatchers("/", "/mathmarathon", "/mathmarathon/settings", "/mathmarathon/game",
                    "/mathmarathon/game/results", "/mathmarathon/rankings").permitAll()
                .anyRequest().authenticated()
            ).formLogin(configurer ->
                configurer.loginPage("/login").permitAll()
            ).logout(configurer -> configurer.permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("qwe")
            .password(passwordEncoder.encode("123"))
            .roles("USER")
            .and()
            .withUser("asd")
            .password(passwordEncoder.encode("123"))
            .roles("ADMIN", "USER");
    }
}