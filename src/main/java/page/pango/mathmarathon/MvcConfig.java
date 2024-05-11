package page.pango.mathmarathon;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/signin").setViewName("signin");
        registry.addViewController("/mathmarathon").setViewName("mathmarathon");
        registry.addViewController("/mathmarathon/settings").setViewName("m_settings");
        registry.addViewController("/mathmarathon/game").setViewName("m_game");
        registry.addViewController("/mathmarathon/game/results").setViewName("m_game_results");
        registry.addViewController("/mathmarathon/game/results/submit").setViewName("m_game_results_submit");
    }
}