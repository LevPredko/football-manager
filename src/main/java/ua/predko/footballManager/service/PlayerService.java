package ua.predko.footballManager.service;

public interface PlayerService {
    void transferPlayer(Long playerId, Long targetTeamId);
}