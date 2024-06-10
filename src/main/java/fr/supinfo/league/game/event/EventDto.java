package fr.supinfo.league.game.event;


import java.util.UUID;

public record EventDto(UUID id, EventType eventType, UUID playerId) {
}
