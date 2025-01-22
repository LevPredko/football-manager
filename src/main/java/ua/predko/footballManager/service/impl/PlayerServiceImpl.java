package ua.predko.footballManager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ua.predko.footballManager.model.Player;
import ua.predko.footballManager.model.dto.PlayerDTO;
import ua.predko.footballManager.repository.PlayerRepository;
import ua.predko.footballManager.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<PlayerDTO> getAllPlayers() {
        return playerRepository.findAll().stream()
                .map(player -> new PlayerDTO(
                        player.getId(),
                        player.getName(),
                        player.getAge(),
                        player.getExperienceMonths(),
                        player.getTransferValue(),
                        player.getTeam() != null ? player.getTeam().getName() : null
                ))
                .toList();
    }

    @Override
    public PlayerDTO getPlayerById(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        return new PlayerDTO(
                player.getId(),
                player.getName(),
                player.getAge(),
                player.getExperienceMonths(),
                player.getTransferValue(),
                player.getTeam() != null ? player.getTeam().getName() : null
        );
    }

    @Override
    public Player createPlayer(Player player) {
        if (player.getAge() < 18) {
            throw new IllegalArgumentException("Player age must be at least 18");
        }
        return playerRepository.save(player);
    }

    @Override
    public Player updatePlayer(Long id, Player playerDetails) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        player.setName(playerDetails.getName());
        player.setAge(playerDetails.getAge());
        player.setExperienceMonths(playerDetails.getExperienceMonths());
        player.setTeam(playerDetails.getTeam());

        return playerRepository.save(player);
    }

    @Override
    public void deletePlayer(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        playerRepository.delete(player);
    }
}
