package fr.supinfo.league.season;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SeasonRepository extends JpaRepository<SeasonEntity, UUID> {
    boolean existsByLabel(String label);
}
