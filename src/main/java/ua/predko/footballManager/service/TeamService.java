package ua.predko.footballManager.service;

import ua.predko.footballManager.model.Team;
import ua.predko.footballManager.model.dto.TeamDTO;

import java.util.List;

public interface TeamService {
    Team createTeam(Team team);
    Team updateTeam(Long id, Team team);
    void deleteTeam(Long id);
    List<Team> getAllTeams();
    Team getTeamById(Long id);
}
