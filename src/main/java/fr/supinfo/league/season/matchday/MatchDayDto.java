package fr.supinfo.league.season.matchday;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record MatchDayDto(UUID id, @NotNull LocalDate date, UUID seasonId) {
}
