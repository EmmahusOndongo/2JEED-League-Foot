package fr.supinfo.league.game;

import fr.supinfo.league.game.comment.CommentDto;
import fr.supinfo.league.game.comment.CommentEntity;
import fr.supinfo.league.game.event.EventDto;
import fr.supinfo.league.game.event.EventEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameMapper {
    List<GameDto> entityToDto(List<GameEntity> entities);

    GameDto entityToDto(GameEntity entity);

    GameEntity dtoToEntity(GameDto dto);

    static CommentDto toCommentDto(CommentEntity entity) {
        return new CommentDto(entity.getId(), entity.getContent(), entity.getAuthor());
    }

    static EventDto toEventDto(EventEntity entity) {
        return new EventDto(entity.getId(), entity.getType(), entity.getPlayer(), entity.getMinute());
    }
}
