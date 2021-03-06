package me.bogeun.abo.controller;

import me.bogeun.abo.domain.*;
import me.bogeun.abo.domain.dto.PlayerCreateForm;
import me.bogeun.abo.domain.dto.UserJoinForm;
import me.bogeun.abo.service.PlayerService;
import me.bogeun.abo.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    PlayerService playerService;

    @Transactional
    @Test
    @DisplayName("Player를 생성하지 않은 User의 /player 처리")
    public void getPlayerInfo_have_no_player() throws Exception {
        UserJoinForm userJoinForm = UserJoinForm.builder()
                .nickname("bogeun")
                .password("password")
                .name("박")
                .email("bogeun@email.com")
                .build();
        User user = userService.joinNewUser(userJoinForm);
        CurrentUser currentUser = new CurrentUser(user);

        mockMvc.perform(get("/player/info")
                .with(csrf())
                .with(user(currentUser)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/player/create"));
    }

    @Transactional
    @Test
    @DisplayName("Player를 생성한 User의 /player 처리")
    public void getPlayerInfo_have_player() throws Exception {
        PlayerCreateForm playerCreateForm = new PlayerCreateForm();
        playerCreateForm.setWeight(180);
        playerCreateForm.setHeight(180);
        playerCreateForm.setPlayHand(PlayHand.RPRB);

        UserJoinForm userJoinForm = UserJoinForm.builder()
                .nickname("bogeun")
                .password("password")
                .name("박")
                .email("bogeun@email.com")
                .build();
        User user = userService.joinNewUser(userJoinForm);
        playerService.enrollPlayer(user, playerCreateForm);
        CurrentUser currentUser = new CurrentUser(user);

        mockMvc.perform(get("/player/info")
                    .with(csrf())
                    .with(user(currentUser)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("player/info"));
    }

}