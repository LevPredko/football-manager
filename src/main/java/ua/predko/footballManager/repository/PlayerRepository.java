package ua.predko.footballManager.repository;

import ua.predko.footballManager.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
