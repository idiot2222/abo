package me.bogeun.abo.controller;

import me.bogeun.abo.domain.CurrentUser;
import me.bogeun.abo.domain.User;
import me.bogeun.abo.domain.UserRole;
import me.bogeun.abo.domain.dto.UserJoinForm;
import me.bogeun.abo.repository.UserRepository;
import me.bogeun.abo.service.UserDetailService;
import me.bogeun.abo.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailService userDetailService;

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
        mockMvc.perform(post("/join")
                        .with(csrf())
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
        mockMvc.perform(post("/join")
                    .with(csrf())
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

    @Test
    @DisplayName("회원 정보 수정 - 성공")
    @Transactional
    public void updateUser_correct() throws Exception {
        UserJoinForm newUser = new UserJoinForm();
        newUser.setNickname("bogeun");
        newUser.setPassword("password");
        newUser.setEmail("bogeun@email.com");
        User user = userService.joinNewUser(newUser);
        UserDetails bogeun = userDetailService.loadUserByUsername("bogeun");

        mockMvc.perform(post("/user/my-page/bogeun")
                    .with(csrf())
                    .with(user(bogeun))
                    .param("password", "password")
                    .param("passwordRepeat", "password")
                    .param("email", "bogeun123@email.com"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        assertEquals(user.getEmail(), "bogeun123@email.com");
    }

    @Test
    @DisplayName("회원 정보 수정 - 실패(잘못된 입력)")
    @Transactional
    public void updateUser_fail_input() throws Exception {
        UserJoinForm newUser = new UserJoinForm();
        newUser.setNickname("bogeun");
        newUser.setPassword("password");
        newUser.setEmail("bogeun@email.com");
        userService.joinNewUser(newUser);
        UserDetails bogeun = userDetailService.loadUserByUsername("bogeun");

        mockMvc.perform(post("/user/my-page/bogeun")
                    .with(csrf())
                    .with(user(bogeun))
                    .param("password", "password123")
                    .param("passwordRepeat", "password")
                    .param("email", "bogeun@email.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/my-page"));
    }

    @Test
    @DisplayName("회원 정보 수정 - 실패(다른 사용자)")
    @Transactional
    public void updateUser_fail_another() throws Exception {
        UserJoinForm newUser = new UserJoinForm();
        newUser.setNickname("bogeun");
        newUser.setPassword("password");
        newUser.setEmail("bogeun@email.com");
        userService.joinNewUser(newUser);

        User anotherUser = User.builder()
                .nickname("bbooggeeuunn")
                .password("password")
                .userRole(UserRole.USER)
                .build();
        CurrentUser currentUser = new CurrentUser(anotherUser);

        mockMvc.perform(post("/user/my-page/bogeun")
                        .with(csrf())
                        .with(user(currentUser))
                        .param("password", "password123")
                        .param("passwordRepeat", "password")
                        .param("email", "bogeun@email.com"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("error"));
    }

    @Test
    @DisplayName("회원 탈퇴 - 성공")
    @Transactional
    public void deleteUser_success() throws Exception {
        UserJoinForm newUser = new UserJoinForm();
        newUser.setNickname("bogeun");
        newUser.setPassword("password");
        newUser.setEmail("bogeun@email.com");
        User user = userService.joinNewUser(newUser);
        CurrentUser currentUser = new CurrentUser(user);
        Long id = user.getId();

        mockMvc.perform(post("/user/delete/"+id)
                    .with(csrf())
                    .with(user(currentUser))
                    .param("password", "password")
                    .param("passwordRepeat", "password"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        assertFalse(userRepository.existsByNickname("bogeun"));
    }

    @Test
    @DisplayName("회원 탈퇴 - 실패")
    @Transactional
    public void deleteUser_fail() throws Exception {
        UserJoinForm newUser1 = new UserJoinForm();
        newUser1.setNickname("bogeun1");
        newUser1.setPassword("password");
        newUser1.setEmail("bogeun1@email.com");
        User user1 = userService.joinNewUser(newUser1);
        UserJoinForm newUser2 = new UserJoinForm();
        newUser2.setNickname("bogeun2");
        newUser2.setPassword("password");
        newUser2.setEmail("bogeun2@email.com");
        User user2 = userService.joinNewUser(newUser2);
        CurrentUser currentUser = new CurrentUser(user2);
        Long id = user1.getId();

        mockMvc.perform(post("/user/delete/"+id)
                    .with(csrf())
                    .with(user(currentUser))
                    .param("password", "password")
                    .param("passwordRepeat", "password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("error"));

        assertFalse(userRepository.existsByNickname("bogeun"));
    }
}