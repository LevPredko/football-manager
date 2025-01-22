package ua.predko.footballManager.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDTO {
    private String playerName;
    private String previousTeam;
    private String newTeam;
    private BigDecimal transferPrice;

    public TransferDTO(String playerName, String previousTeam, String newTeam, BigDecimal transferPrice) {
        this.playerName = playerName;
        this.previousTeam = previousTeam;
        this.newTeam = newTeam;
        this.transferPrice = transferPrice;
    }
}