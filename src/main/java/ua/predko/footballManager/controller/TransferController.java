package ua.predko.footballManager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.predko.footballManager.model.dto.TransferDTO;
import ua.predko.footballManager.service.TransferService;

import java.util.List;

@RestController
@RequestMapping("/transfers")
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping()
    public ResponseEntity<List<TransferDTO>> getAllTransfers() {
        List<TransferDTO> transfers = transferService.getAllTransfers();
        return ResponseEntity.ok(transfers);
    }

    @PostMapping()
    public void transferPlayer(@RequestParam Long playerId, @RequestParam Long targetTeamId) {
        transferService.transferPlayer(playerId, targetTeamId);
    }
}