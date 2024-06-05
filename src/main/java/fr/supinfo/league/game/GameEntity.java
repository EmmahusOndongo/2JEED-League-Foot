package fr.supinfo.league.game;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class GameEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String description;
    private LocalTime startTime;
    private UUID matchDayId;
    private UUID homeTeamId;
    private UUID visitorTeamId;
}
