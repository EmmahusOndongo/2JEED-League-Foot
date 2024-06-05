package fr.supinfo.league.game;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.UUID;

public record GameDto(UUID id, String description, @NotNull UUID matchDayId, @NotNull UUID homeTeamId,
                      @NotNull UUID visitorTeamId,
                      @NotNull LocalTime startTime) {
}