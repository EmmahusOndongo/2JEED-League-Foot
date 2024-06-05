package fr.supinfo.league.season.matchday;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class MatchDayEntity {
    @Id
    private UUID id;
    private UUID seasonId;
    private LocalDate date;
    @ElementCollection
    private Set<UUID> matchIdList = new HashSet<>();
}
