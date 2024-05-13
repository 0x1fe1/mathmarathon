package page.pango.mathmarathon.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import page.pango.mathmarathon.entity.User;
import page.pango.mathmarathon.entity.UserRole;
import page.pango.mathmarathon.repositories.UserRepository;
import page.pango.mathmarathon.repositories.UserRoleRepository;
import page.pango.mathmarathon.service.AppUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new AppUserDetailsService();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(registry -> registry
                .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
                .requestMatchers("/", "/mathmarathon", "/mathmarathon/settings", "/mathmarathon/game",
                    "/mathmarathon/game/results", "/mathmarathon/rankings").permitAll()
                .requestMatchers("/login", "/register").permitAll()
                .anyRequest().authenticated()
            ).formLogin(form -> form
                .loginPage("/login")
                .successHandler(successHandler())
                .permitAll()
            ).logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            ).build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        var successHandler = new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                var username = authentication.getName();
                User user = userRepository.getUserByName(username);
                UserRole userRole = userRoleRepository.getUserRoleById(user.getId());

                if (userRole == null || userRole.role == null) {
                    return "/";
                }

                return switch (userRole.role) {
                    case USER -> "/";
                };
            }
        };
        successHandler.setUseReferer(true);
        return successHandler;
    }
}