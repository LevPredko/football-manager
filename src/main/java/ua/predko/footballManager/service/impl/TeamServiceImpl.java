package ua.predko.footballManager.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import ua.predko.footballManager.model.Team;
import ua.predko.footballManager.model.Player;
import ua.predko.footballManager.model.dto.TeamDTO;
import ua.predko.footballManager.repository.TeamRepository;
import ua.predko.footballManager.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(team -> new TeamDTO(
                        team.getId(),
                        team.getName(),
                        team.getBalance(),
                        team.getCommission(),
                        team.getPlayers().stream().map(Player::getName).toList()
                ))
                .toList();
    }

    @Override
    public TeamDTO getTeamById(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Team not found"));
        return new TeamDTO(
                team.getId(),
                team.getName(),
                team.getBalance(),
                team.getCommission(),
                team.getPlayers().stream().map(Player::getName).toList()
        );
    }

    @Override
    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Team updateTeam(Long id, Team team) {
        return teamRepository.findById(id).map(existingTeam -> {
            existingTeam.setName(team.getName());
            existingTeam.setBalance(team.getBalance());
            existingTeam.setCommission(team.getCommission());
            return teamRepository.save(existingTeam);
        }).orElseThrow(() -> new IllegalArgumentException("Team not found"));
    }

    @Override
    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }
}
