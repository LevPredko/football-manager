package ua.predko.footballManager.service;

import ua.predko.footballManager.model.dto.TransferDTO;

import java.util.List;

public interface TransferService {
    List<TransferDTO> getAllTransfers();

    TransferDTO transferPlayer(Long playerId, Long targetTeamId);
}