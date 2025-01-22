package ua.predko.footballManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.predko.footballManager.model.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}