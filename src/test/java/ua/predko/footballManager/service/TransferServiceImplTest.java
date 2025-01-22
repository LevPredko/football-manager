package ua.predko.footballManager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.predko.footballManager.model.Player;
import ua.predko.footballManager.model.Team;
import ua.predko.footballManager.model.Transfer;
import ua.predko.footballManager.model.dto.TransferDTO;
import ua.predko.footballManager.repository.PlayerRepository;
import ua.predko.footballManager.repository.TeamRepository;
import ua.predko.footballManager.repository.TransferRepository;
import ua.predko.footballManager.service.impl.TransferServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TransferRepository transferRepository;

    @InjectMocks
    private TransferServiceImpl transferService;

    private Player player;
    private Team currentTeam;
    private Team targetTeam;
    private Transfer transfer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        currentTeam = new Team();
        currentTeam.setId(1L);
        currentTeam.setName("Current Team");
        currentTeam.setBalance(5_000_000.0);
        currentTeam.setCommission(7);

        targetTeam = new Team();
        targetTeam.setId(2L);
        targetTeam.setName("Target Team");
        targetTeam.setBalance(10_000_000.0);

        player = new Player();
        player.setId(1L);
        player.setName("Test Player");
        player.setAge(25);
        player.setExperienceMonths(24);
        player.setTeam(currentTeam);

        transfer = new Transfer();
        transfer.setPlayer(player);
        transfer.setPreviousTeam(currentTeam);
        transfer.setNewTeam(targetTeam);
        transfer.setTransferPrice(BigDecimal.valueOf(1_000_000.0));
    }

    @Test
    void getAllTransfers_ShouldReturnListOfTransferDTO() {
        when(transferRepository.findAll()).thenReturn(List.of(transfer));

        List<TransferDTO> transfers = transferService.getAllTransfers();

        assertNotNull(transfers);
        assertEquals(1, transfers.size());
        TransferDTO dto = transfers.get(0);
        assertEquals(player.getName(), dto.getPlayerName());
        assertEquals(currentTeam.getName(), dto.getPreviousTeam());
        assertEquals(targetTeam.getName(), dto.getNewTeam());
        assertEquals(transfer.getTransferPrice(), dto.getTransferPrice());
    }

    @Test
    void transferPlayer_ShouldPerformTransfer_WhenValidDataProvided() {
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(targetTeam));
        when(playerRepository.save(any(Player.class))).thenReturn(player);
        when(teamRepository.save(any(Team.class))).thenReturn(targetTeam);

        TransferDTO dto = transferService.transferPlayer(1L, 2L);

        assertNotNull(dto);
        assertEquals(player.getName(), dto.getPlayerName());
        assertEquals(currentTeam.getName(), dto.getPreviousTeam());
        assertEquals(targetTeam.getName(), dto.getNewTeam());
        assertNotNull(dto.getTransferPrice());
        verify(transferRepository, times(1)).save(any(Transfer.class));
        verify(playerRepository, times(1)).save(player);
        verify(teamRepository, times(1)).save(currentTeam);
        verify(teamRepository, times(1)).save(targetTeam);
    }

    @Test
    void transferPlayer_ShouldThrowException_WhenPlayerNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                transferService.transferPlayer(1L, 2L)
        );

        assertEquals("Player not found", exception.getMessage());
    }

    @Test
    void transferPlayer_ShouldThrowException_WhenTargetTeamNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(teamRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                transferService.transferPlayer(1L, 2L)
        );

        assertEquals("Target team not found", exception.getMessage());
    }

    @Test
    void transferPlayer_ShouldThrowException_WhenInsufficientBalance() {
        targetTeam.setBalance(100_000.0);
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(targetTeam));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                transferService.transferPlayer(1L, 2L)
        );

        assertEquals("Insufficient balance", exception.getMessage());
    }
}
