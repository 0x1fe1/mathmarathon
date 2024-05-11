package page.pango.mathmarathon;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mathmarathon/game/results")
public class GameResultController {

    @PostMapping("/submit")
    public ResponseEntity<String> submitGameResult(@RequestBody GameResult gameResult) {
        // You can access the authenticated user's information
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        // Implement your logic to save the game result to the database

        return ResponseEntity.ok("Game result submitted successfully!");
    }
}
