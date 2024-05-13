package page.pango.mathmarathon.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import page.pango.mathmarathon.entity.UserDTO;
import page.pango.mathmarathon.service.UserService;

@Controller
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/register")
    public String regGet(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String regPost(@ModelAttribute("user") UserDTO newUser, HttpServletRequest request) throws ServletException {
        if (!newUser.getPassword().equals(newUser.getConfirmPassword())) {
            return "redirect:/register?error";
        }
        if (userService.addUser(newUser)) {
            request.login(newUser.getName(), newUser.getPassword());
            return "redirect:/";
        }

        return "redirect:/register?exists";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}