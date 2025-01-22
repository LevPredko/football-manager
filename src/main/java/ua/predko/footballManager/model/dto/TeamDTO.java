package ua.predko.footballManager.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeamDTO {
    private Long id;
    private String name;
    private double balance;
    private int commission;
    private List<String> playerNames;

    public TeamDTO(Long id, String name, double balance, int commission, List<String> playerNames) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.commission = commission;
        this.playerNames = playerNames;
    }
}