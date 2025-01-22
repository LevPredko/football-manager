package ua.predko.footballManager.service;

import ua.predko.footballManager.model.Player;
import ua.predko.footballManager.model.dto.PlayerDTO;

import java.util.List;

public interface PlayerService {

    List<PlayerDTO> getAllPlayers();

    PlayerDTO getPlayerById(Long id);

    Player createPlayer(Player player);

    Player updatePlayer(Long id, Player playerDetails);

    void deletePlayer(Long id);
}