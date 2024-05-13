package page.pango.mathmarathon.controller;

import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import page.pango.mathmarathon.dto.UserDto;
import page.pango.mathmarathon.service.UserService;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/app";
        }
        return "registration";
    }

    @PostMapping("/register")
    public RedirectView registerUserAccount(@Valid final UserDto accountDto) {
        try {
            userService.registerNewUserAccount(accountDto);
        } catch (BadCredentialsException | EntityExistsException e) {
            return new RedirectView("/register?error=" + e.getMessage());
        }
        return new RedirectView("/login?register=true");
    }
}