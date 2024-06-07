package fr.supinfo.league.game;

import fr.supinfo.league.game.comment.CommentDto;
import fr.supinfo.league.game.comment.CommentEntity;
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

}
