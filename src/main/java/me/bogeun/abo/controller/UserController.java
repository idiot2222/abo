package me.bogeun.abo.controller;

import lombok.RequiredArgsConstructor;
import me.bogeun.abo.domain.dto.UserJoinForm;
import me.bogeun.abo.domain.dto.UserLoginForm;
import me.bogeun.abo.service.UserService;
import me.bogeun.abo.valid.UserJoinValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final UserJoinValidator userJoinValidator;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(userJoinValidator);
    }

    @GetMapping("/join")
    public String getJoin(Model model) {
        model.addAttribute("userJoinForm", new UserJoinForm());
        return "user/join";
    }

    @PostMapping("/join")
    public String postJoin(@Valid UserJoinForm userJoinForm, BindingResult result) {
        if(result.hasErrors()) {
            return"user/join";
        }

        userService.joinNewUser(userJoinForm);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute(new UserLoginForm());
        return "user/login";
    }
}
