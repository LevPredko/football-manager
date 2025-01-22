package ua.predko.footballManager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.predko.footballManager.model.Player;
import ua.predko.footballManager.model.Team;
import ua.predko.footballManager.model.Transfer;
import ua.predko.footballManager.model.dto.TransferDTO;
import ua.predko.footballManager.repository.PlayerRepository;
import ua.predko.footballManager.repository.TeamRepository;
import ua.predko.footballManager.repository.TransferRepository;
import ua.predko.footballManager.service.TransferService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferServiceImpl implements TransferService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final TransferRepository transferRepository;

    @Autowired
    public TransferServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository, TransferRepository transferRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.transferRepository = transferRepository;
    }

    @Override
    @Transactional
    public List<TransferDTO> getAllTransfers() {
        List<Transfer> transfers = transferRepository.findAll();

        return transfers.stream()
                .map(transfer -> new TransferDTO(
                        transfer.getPlayer().getName(),
                        transfer.getPreviousTeam().getName(),
                        transfer.getNewTeam().getName(),
                        transfer.getTransferPrice()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TransferDTO transferPlayer(Long playerId, Long targetTeamId) {
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

        Transfer transfer = new Transfer();
        transfer.setPlayer(player);
        transfer.setPreviousTeam(currentTeam);
        transfer.setNewTeam(targetTeam);
        transfer.setTransferPrice(BigDecimal.valueOf(totalCost));
        transferRepository.save(transfer);

        player.setTeam(targetTeam);
        playerRepository.save(player);

        teamRepository.save(currentTeam);
        teamRepository.save(targetTeam);

        return new TransferDTO(
                player.getName(),
                currentTeam.getName(),
                targetTeam.getName(),
                BigDecimal.valueOf(totalCost)
        );
    }
}