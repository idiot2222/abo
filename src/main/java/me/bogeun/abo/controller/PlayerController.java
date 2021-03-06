package me.bogeun.abo.controller;

import lombok.RequiredArgsConstructor;
import me.bogeun.abo.domain.CurrentUser;
import me.bogeun.abo.domain.User;
import me.bogeun.abo.domain.dto.PlayerCreateForm;
import me.bogeun.abo.service.PlayerService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/player/info")
    public String getPlayerInfo(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.getUser().getPlayer() == null) {
            return "redirect:/player/create";
        }

        return "player/info";
    }

    @GetMapping("/player/create")
    public String getPlayerCreate(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        model.addAttribute(new PlayerCreateForm());

        return "player/create";
    }

    @PostMapping("/player/create")
    public String postPlayerCreate(@Valid PlayerCreateForm playerCreateForm,
       BindingResult result, @AuthenticationPrincipal CurrentUser currentUser) {
        if(result.hasErrors()) {
            return "player/create";
        }

        User user = currentUser.getUser();
        playerService.enrollPlayer(user, playerCreateForm);
        return "redirect:/";
    }
}
