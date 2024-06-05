package fr.supinfo.league.team;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TeamDto(UUID id, @NotNull String name, String description) {
}
