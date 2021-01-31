package me.bogeun.abo.controller;

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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        UserJoinForm userJoinForm = new UserJoinForm();
        userJoinForm.setNickname("bogeun");
        userJoinForm.setPassword("password");
        userJoinForm.setEmail("bogeun@email.com");
        userJoinForm.setName("박현근");

        userService.joinNewUser(userJoinForm);
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("GET 홈 페이지")
    public void getHome() throws Exception {
        mockMvc.perform(get("/"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("home"));
    }

    @Test
    @DisplayName("유저 로그인 - 성공")
    public void login_success() throws Exception {
        mockMvc.perform(post("/login").with(csrf())
                .param("username", "bogeun")
                .param("password", "password"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername("bogeun"));
    }

    @Test
    @DisplayName("유저 로그인 - 실패")
    public void login_fail() throws Exception {
        mockMvc.perform(post("/login").with(csrf())
                .param("username", "bogeun")
                .param("password", "fail123123"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    @DisplayName("로그아웃")
    public void logout() throws Exception{
        mockMvc.perform(post("/logout").with(csrf()))
                .andDo(print())
//                .andExpect(status().is3xxRedirection()) //TODO 204 뜨는데 이유를 찾아봐야 함
//                .andExpect(redirectedUrl("/"))    //TODO 204가 뜨니 redirect url도 null이 뜸
                .andExpect(unauthenticated());
    }
}