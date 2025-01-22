package ua.predko.footballManager.model.dto;

import java.util.List;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(List<String> playerNames) {
        this.playerNames = playerNames;
    }
}