package me.bogeun.abo.controller;

import me.bogeun.abo.domain.User;
import me.bogeun.abo.domain.dto.UserJoinForm;
import me.bogeun.abo.repository.UserRepository;
import me.bogeun.abo.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET 회원 가입 페이지")
    public void getJoin() throws Exception {
        mockMvc.perform(get("/join"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("user/join"));
    }

    @Test
    @DisplayName("회원 가입 - 정상")
    public void postJoin_correct() throws Exception {
        mockMvc.perform(post("/join").with(csrf())
                    .param("nickname", "bogeun123")
                    .param("password", "password")
                    .param("passwordRepeat", "password")
                    .param("email", "email@naver.com")
                    .param("name", "박보근"))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/"));

        User user = userRepository.findByNickname("bogeun123");
        assertNotNull(user);
    }

    @Test
    @DisplayName("회원 가입 - 실패")
    public void postJoin_fail() throws Exception {
        mockMvc.perform(post("/join").with(csrf())
                    .param("nickname", "bo")
                    .param("password", "password")
                    .param("passwordRepeat", "password123")
                    .param("email", "email")
                    .param("name", "박"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("user/join"));

        User user = userRepository.findByNickname("bo");
        assertNull(user);
    }
}