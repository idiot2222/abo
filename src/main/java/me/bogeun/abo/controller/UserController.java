package me.bogeun.abo.controller;

import lombok.RequiredArgsConstructor;
import me.bogeun.abo.domain.CurrentUser;
import me.bogeun.abo.domain.User;
import me.bogeun.abo.domain.dto.UserJoinForm;
import me.bogeun.abo.domain.dto.UserUpdateForm;
import me.bogeun.abo.repository.UserRepository;
import me.bogeun.abo.service.UserService;
import me.bogeun.abo.valid.UserJoinValidator;
import me.bogeun.abo.valid.UserUpdateValidator;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserJoinValidator userJoinValidator;
    private final UserUpdateValidator userUpdateValidator;

    @GetMapping("/join")
    public String getJoin(Model model) {
        model.addAttribute("userJoinForm", new UserJoinForm());
        return "user/join";
    }

    @PostMapping("/join")
    public String postJoin(@Valid UserJoinForm userJoinForm, BindingResult result) {
        userJoinValidator.validate(userJoinForm, result);
        if(result.hasErrors()) {
            return"user/join";
        }

        userService.joinNewUser(userJoinForm);
        return "redirect:/";
    }

    @GetMapping("/user/my-page/{nickname}")
    public String getMyPage(@PathVariable String nickname, Model model, @AuthenticationPrincipal CurrentUser currentUser) throws IllegalAccessException {
        User foundUser = userRepository.findByNickname(nickname);

        if(foundUser == null) {
            throw new IllegalAccessException(nickname+"에 해당하는 사용자가 없습니다.");
        }

        model.addAttribute("userUpdateForm", new UserUpdateForm());
        model.addAttribute("user", foundUser);
        model.addAttribute("isOwner", foundUser.getId() == currentUser.getUser().getId());

        return "user/my-page";
    }

    @Transactional
    @PostMapping("/user/my-page/{nickname}")
    public String updateMyPage(@PathVariable String nickname, @Valid UserUpdateForm updateForm, BindingResult result, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        // 해당 닉네임 유저 체크
        User foundUser = userRepository.findByNickname(nickname);
        if (foundUser == null) {
            throw new IllegalArgumentException(nickname + "에 해당하는 사용자를 찾을 수 없습니다.");
        }

        if(foundUser.getId() != currentUser.getUser().getId()) {
            return "/error";
        }

        model.addAttribute(foundUser);

        // 유저 업데이트 폼 벨리데이션
        userUpdateValidator.validate(updateForm, result);
        if (result.hasErrors()) {
            return "user/my-page";
        }

        userService.updateUser(foundUser, updateForm);
        return "redirect:/";
    }
}
