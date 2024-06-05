package fr.supinfo.league.season.matchday;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MatchDayRepository extends JpaRepository<MatchDayEntity, UUID> {

    List<MatchDayEntity> findBySeasonId(UUID seasonId);

    boolean existsBySeasonIdAndDate(UUID seasonUuid, LocalDate date);

    Optional<MatchDayEntity> findByDate(LocalDate date);
}
