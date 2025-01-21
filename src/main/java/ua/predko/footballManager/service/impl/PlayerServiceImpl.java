package ua.predko.footballManager.service.impl;

import ua.predko.footballManager.model.Player;
import ua.predko.footballManager.model.Team;
import ua.predko.footballManager.repository.PlayerRepository;
import ua.predko.footballManager.repository.TeamRepository;
import ua.predko.footballManager.service.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional
    @Override
    public void transferPlayer(Long playerId, Long targetTeamId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));
        Team targetTeam = teamRepository.findById(targetTeamId)
                .orElseThrow(() -> new IllegalArgumentException("Target team not found"));
        Team currentTeam = player.getTeam();

        double transferValue = player.getExperienceMonths() * 100_000.0 / player.getAge();
        double commission = transferValue * currentTeam.getCommission() / 100;
        double totalCost = transferValue + commission;

        if (targetTeam.getBalance() < totalCost) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        targetTeam.setBalance(targetTeam.getBalance() - totalCost);
        currentTeam.setBalance(currentTeam.getBalance() + totalCost);

        player.setTeam(targetTeam);
        playerRepository.save(player);
    }
}
