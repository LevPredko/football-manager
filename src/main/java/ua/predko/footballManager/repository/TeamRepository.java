package ua.predko.footballManager.repository;

import ua.predko.footballManager.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}

