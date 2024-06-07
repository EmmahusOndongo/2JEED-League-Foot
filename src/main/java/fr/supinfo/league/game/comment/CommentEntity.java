package fr.supinfo.league.game.comment;

import fr.supinfo.league.game.GameEntity;
import jakarta.persistence.Entity;
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
public class CommentEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String content;
    private String author;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameEntity game;
}
