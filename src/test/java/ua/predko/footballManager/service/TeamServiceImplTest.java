package ua.predko.footballManager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.predko.footballManager.model.Team;
import ua.predko.footballManager.model.dto.TeamDTO;
import ua.predko.footballManager.repository.TeamRepository;
import ua.predko.footballManager.service.impl.TeamServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamServiceImpl teamService;

    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setId(1L);
        team.setName("Test Team");
        team.setBalance(1000.0);
        team.setCommission(5);
        team.setPlayers(List.of());
    }

    @Test
    void getAllTeams_ShouldReturnListOfTeamDTO() {
        when(teamRepository.findAll()).thenReturn(List.of(team));

        List<TeamDTO> result = teamService.getAllTeams();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Team", result.get(0).getName());
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    void getTeamById_ShouldReturnTeamDTO_WhenTeamExists() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));

        TeamDTO result = teamService.getTeamById(1L);

        assertNotNull(result);
        assertEquals("Test Team", result.getName());
        verify(teamRepository, times(1)).findById(1L);
    }

    @Test
    void getTeamById_ShouldThrowException_WhenTeamNotFound() {
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> teamService.getTeamById(1L));

        assertEquals("Team not found", exception.getMessage());
        verify(teamRepository, times(1)).findById(1L);
    }

    @Test
    void createTeam_ShouldSaveAndReturnTeam() {
        when(teamRepository.save(team)).thenReturn(team);

        Team result = teamService.createTeam(team);

        assertNotNull(result);
        assertEquals("Test Team", result.getName());
        verify(teamRepository, times(1)).save(team);
    }

    @Test
    void updateTeam_ShouldUpdateAndReturnTeam_WhenTeamExists() {
        Team updatedTeam = new Team();
        updatedTeam.setName("Updated Team");
        updatedTeam.setBalance(2000.0);
        updatedTeam.setCommission(5);

        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(teamRepository.save(any(Team.class))).thenReturn(updatedTeam);

        Team result = teamService.updateTeam(1L, updatedTeam);

        assertNotNull(result);
        assertEquals("Updated Team", result.getName());
        assertEquals(2000.0, result.getBalance());
        verify(teamRepository, times(1)).findById(1L);
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    void updateTeam_ShouldThrowException_WhenTeamNotFound() {
        Team updatedTeam = new Team();
        updatedTeam.setName("Updated Team");

        when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> teamService.updateTeam(1L, updatedTeam));

        assertEquals("Team not found", exception.getMessage());
        verify(teamRepository, times(1)).findById(1L);
        verify(teamRepository, never()).save(any(Team.class));
    }

    @Test
    void deleteTeam_ShouldDeleteTeam_WhenIdExists() {
        doNothing().when(teamRepository).deleteById(1L);

        teamService.deleteTeam(1L);

        verify(teamRepository, times(1)).deleteById(1L);
    }
}
