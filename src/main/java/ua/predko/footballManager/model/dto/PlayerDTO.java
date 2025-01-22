package ua.predko.footballManager.model.dto;


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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getExperienceMonths() {
        return experienceMonths;
    }

    public void setExperienceMonths(int experienceMonths) {
        this.experienceMonths = experienceMonths;
    }

    public double getTransferValue() {
        return transferValue;
    }

    public void setTransferValue(double transferValue) {
        this.transferValue = transferValue;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}

