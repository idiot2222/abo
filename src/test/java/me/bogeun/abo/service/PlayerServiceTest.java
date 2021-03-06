package me.bogeun.abo.service;

import me.bogeun.abo.domain.PlayHand;
import me.bogeun.abo.domain.Player;
import me.bogeun.abo.domain.Position;
import me.bogeun.abo.domain.User;
import me.bogeun.abo.domain.dto.PlayerCreateForm;
import me.bogeun.abo.domain.dto.UserJoinForm;
import me.bogeun.abo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PlayerServiceTest {

    @Autowired
    PlayerService playerService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    void createPlayer() {
        UserJoinForm userJoinForm = new UserJoinForm();
        userJoinForm.setNickname("bogeun");
        userJoinForm.setPassword("password");
        userJoinForm.setName("박");
        userJoinForm.setEmail("bogeun@email.com");
        User user = userService.joinNewUser(userJoinForm);

        PlayerCreateForm playerCreateForm = new PlayerCreateForm();
        playerCreateForm.setHeight(178);
        playerCreateForm.setWeight(80);
        playerCreateForm.setPlayHand(PlayHand.RPRB);
        playerCreateForm.setPosition(Position.PITCHER);

        Player newPlayer = playerService.enrollPlayer(user, playerCreateForm);
        Player player = userRepository.findByNickname("bogeun").getPlayer();

        assertThat(player).isEqualTo(newPlayer);
    }
}