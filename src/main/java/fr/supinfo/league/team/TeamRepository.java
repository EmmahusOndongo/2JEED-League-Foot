package fr.supinfo.league.team;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeamRepository extends JpaRepository<TeamEntity, UUID> {
}
