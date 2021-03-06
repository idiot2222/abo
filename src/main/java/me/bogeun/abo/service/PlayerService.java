package me.bogeun.abo.service;

import lombok.RequiredArgsConstructor;
import me.bogeun.abo.domain.Player;
import me.bogeun.abo.domain.Positions;
import me.bogeun.abo.domain.User;
import me.bogeun.abo.domain.dto.PlayerCreateForm;
import me.bogeun.abo.repository.PlayerRepository;
import me.bogeun.abo.repository.PositionsRepository;
import me.bogeun.abo.repository.UserRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlayerService {

    private final UserRepository userRepository;
    private final PositionsRepository positionsRepository;
    private final PlayerRepository playerRepository;

    public Player enrollPlayer(User user, PlayerCreateForm playerCreateForm) {
        Player newPlayer = createPlayer(playerCreateForm);

        user.enrollPlayer(newPlayer);
        newPlayer.enrollUser(user);

        playerRepository.save(newPlayer);
        userRepository.save(user);

        return newPlayer;
    }

    private Player createPlayer(PlayerCreateForm playerCreateForm) {
        Player player = Player.builder()
                .weight(playerCreateForm.getWeight())
                .height(playerCreateForm.getHeight())
                .playHand(playerCreateForm.getPlayHand())
                .build();

        Positions newPosition = Positions.builder()
                .position(playerCreateForm.getPosition())
                .player(player)
                .build();
        player.addPosition(newPosition);

        playerRepository.save(player);
        positionsRepository.save(newPosition);
        return player;
    }
}
