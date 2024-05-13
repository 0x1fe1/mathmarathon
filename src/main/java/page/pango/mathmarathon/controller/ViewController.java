package page.pango.mathmarathon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/mathmarathon")
    public String mathmarathon() {
        return "mathmarathon";
    }

    @RequestMapping("/mathmarathon/settings")
    public String m_settings() {
        return "m_settings";
    }

    @RequestMapping("/mathmarathon/game")
    public String m_game() {
        return "m_game";
    }

    @RequestMapping("/mathmarathon/game/results")
    public String m_game_results() {
        return "m_game_results";
    }

    @RequestMapping("/mathmarathon/rankings")
    public String m_rankings() {
        return "m_rankings";
    }
}
