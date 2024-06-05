package fr.supinfo.league.season;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SeasonDto(UUID id, @NotNull String label) {
}
