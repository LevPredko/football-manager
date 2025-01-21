package ua.predko.footballManager.service.impl;

import ua.predko.footballManager.model.Team;
import ua.predko.footballManager.repository.TeamRepository;
import ua.predko.footballManager.service.TeamService;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
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

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Team not found"));
    }
}
