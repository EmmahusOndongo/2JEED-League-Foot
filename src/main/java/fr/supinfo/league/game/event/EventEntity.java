package fr.supinfo.league.game.event;

import fr.supinfo.league.game.GameEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class EventEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private EventType type;

    private String player;
    private int minute;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameEntity game;
}

enum EventType {
    GOAL,
    YELLOW_CARD,
    RED_CARD
}
