package page.pango.mathmarathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
}
