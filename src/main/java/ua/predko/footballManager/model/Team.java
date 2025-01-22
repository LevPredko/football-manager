package ua.predko.footballManager.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double balance;
    private int commission;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players;
}