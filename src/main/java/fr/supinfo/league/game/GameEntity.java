package fr.supinfo.league.game;

import fr.supinfo.league.game.comment.CommentEntity;
import fr.supinfo.league.game.event.EventEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;
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
    private LocalTime endTime;
    private UUID matchDayId;
    private UUID homeTeamId;
    private UUID visitorTeamId;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventEntity> events;
}
