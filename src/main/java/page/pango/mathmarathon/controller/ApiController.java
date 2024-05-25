package page.pango.mathmarathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import page.pango.mathmarathon.entity.UserRankDTO;
import page.pango.mathmarathon.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private final UserService userService;

    public ApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/rankings")
    public List<UserRankDTO> getRankings() {
        return userService.getRankings();
    }

    @GetMapping("/deleteFakeUsers")
    public void deleteAllUsers() {
        userService.deleteFakeUsers();
    }

    @GetMapping("/addFakeUsers")
    public void addFakeUsers() {
        userService.addFakeUsers(10);
    }

    @PostMapping("/updateUserRanking")
    public ResponseEntity<String> updateUserRanking(@RequestBody UserRankDTO userRankDTO) {
        boolean success = userService.updateUserRank(userRankDTO.getName(), userRankDTO.getRank());
        if (success) {
            return ResponseEntity.ok("User ranking updated successfully");
        } else {
            return ResponseEntity.status(400).body("Failed to update user ranking");
        }
    }
}
