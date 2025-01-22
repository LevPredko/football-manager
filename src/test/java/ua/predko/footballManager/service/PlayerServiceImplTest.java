package ua.predko.footballManager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.predko.footballManager.model.Player;
import ua.predko.footballManager.model.Team;
import ua.predko.footballManager.model.dto.PlayerDTO;
import ua.predko.footballManager.repository.PlayerRepository;
import ua.predko.footballManager.service.impl.PlayerServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

    private Player player;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        player = new Player();
        player.setId(1L);
        player.setName("John Doe");
        player.setAge(25);
        player.setExperienceMonths(36);
        player.setTransferValue(50000.0);

        Team team = new Team();
        team.setName("Test Team");
        player.setTeam(team);
    }

    @Test
    void getAllPlayers_ShouldReturnListOfPlayerDTOs() {
        when(playerRepository.findAll()).thenReturn(List.of(player));

        List<PlayerDTO> playerDTOs = playerService.getAllPlayers();

        assertNotNull(playerDTOs);
        assertEquals(1, playerDTOs.size());
        assertEquals("John Doe", playerDTOs.get(0).getName());
        assertEquals("Test Team", playerDTOs.get(0).getTeamName());

        verify(playerRepository, times(1)).findAll();
    }

    @Test
    void getPlayerById_ShouldReturnPlayerDTO_WhenPlayerExists() {
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        PlayerDTO playerDTO = playerService.getPlayerById(1L);

        assertNotNull(playerDTO);
        assertEquals("John Doe", playerDTO.getName());
        assertEquals(25, playerDTO.getAge());
        assertEquals("Test Team", playerDTO.getTeamName());

        verify(playerRepository, times(1)).findById(1L);
    }

    @Test
    void getPlayerById_ShouldThrowException_WhenPlayerDoesNotExist() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> playerService.getPlayerById(1L));

        assertEquals("Player not found", exception.getMessage());
        verify(playerRepository, times(1)).findById(1L);
    }

    @Test
    void createPlayer_ShouldSavePlayer_WhenAgeIsValid() {
        when(playerRepository.save(player)).thenReturn(player);

        Player savedPlayer = playerService.createPlayer(player);

        assertNotNull(savedPlayer);
        assertEquals("John Doe", savedPlayer.getName());
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    void createPlayer_ShouldThrowException_WhenAgeIsInvalid() {
        player.setAge(17);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> playerService.createPlayer(player));

        assertEquals("Player age must be at least 18", exception.getMessage());
        verify(playerRepository, never()).save(player);
    }

    @Test
    void updatePlayer_ShouldUpdateAndSavePlayer_WhenPlayerExists() {
        Player updatedDetails = new Player();
        updatedDetails.setName("Updated Name");
        updatedDetails.setAge(28);
        updatedDetails.setExperienceMonths(48);

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(playerRepository.save(player)).thenReturn(player);

        Player updatedPlayer = playerService.updatePlayer(1L, updatedDetails);

        assertNotNull(updatedPlayer);
        assertEquals("Updated Name", updatedPlayer.getName());
        assertEquals(28, updatedPlayer.getAge());
        assertEquals(48, updatedPlayer.getExperienceMonths());

        verify(playerRepository, times(1)).findById(1L);
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    void updatePlayer_ShouldThrowException_WhenPlayerDoesNotExist() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        Player updatedDetails = new Player();
        updatedDetails.setName("Updated Name");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> playerService.updatePlayer(1L, updatedDetails));

        assertEquals("Player not found", exception.getMessage());
        verify(playerRepository, times(1)).findById(1L);
        verify(playerRepository, never()).save(any());
    }

    @Test
    void deletePlayer_ShouldDeletePlayer_WhenPlayerExists() {
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        playerService.deletePlayer(1L);

        verify(playerRepository, times(1)).findById(1L);
        verify(playerRepository, times(1)).delete(player);
    }

    @Test
    void deletePlayer_ShouldThrowException_WhenPlayerDoesNotExist() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> playerService.deletePlayer(1L));

        assertEquals("Player not found", exception.getMessage());
        verify(playerRepository, times(1)).findById(1L);
        verify(playerRepository, never()).delete(any());
    }
}
