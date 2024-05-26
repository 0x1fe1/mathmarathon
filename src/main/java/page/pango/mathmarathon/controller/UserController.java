package page.pango.mathmarathon.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
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
    private final RequestCache requestCache = new HttpSessionRequestCache();

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/register")
    public String regGet(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String regPost(@ModelAttribute("user") UserDTO newUser, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if (!newUser.getPassword().equals(newUser.getConfirmPassword())) {
            return "redirect:/register?error";
        }
        if (userService.addUser(newUser)) {
            request.login(newUser.getName(), newUser.getPassword());
            response.addCookie(new Cookie("USERNAME", newUser.getName()));

            SavedRequest savedRequest = requestCache.getRequest(request, response);
            if (savedRequest != null) {
                return "redirect:" + savedRequest.getRedirectUrl();
            }
            return "redirect:/";
        }
        return "redirect:/register?exists";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}