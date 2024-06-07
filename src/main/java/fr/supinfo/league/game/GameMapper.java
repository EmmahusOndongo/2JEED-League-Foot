package fr.supinfo.league.game;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameMapper {
    List<GameDto> entityToDto(List<GameEntity> entities);

    GameDto entityToDto(GameEntity entity);

    GameEntity dtoToEntity(GameDto dto);
}
