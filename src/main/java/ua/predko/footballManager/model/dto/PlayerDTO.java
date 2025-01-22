package ua.predko.footballManager.model.dto;

import lombok.Data;

@Data
public class PlayerDTO {
    private Long id;
    private String name;
    private int age;
    private int experienceMonths;
    private double transferValue;
    private String teamName;

    public PlayerDTO(Long id, String name, int age, int experienceMonths, double transferValue, String teamName) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.experienceMonths = experienceMonths;
        this.transferValue = transferValue;
        this.teamName = teamName;
    }
}
