package fr.supinfo.league.game.event;

import fr.supinfo.league.game.GameEntity;
import jakarta.persistence.*;
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
    private EventType eventType;

    private UUID playerId;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameEntity game;
}
