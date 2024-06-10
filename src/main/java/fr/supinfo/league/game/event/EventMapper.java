package fr.supinfo.league.game.event;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDto entityToDto(EventEntity entity);
    EventEntity dtoToEntity(EventDto dto);
}
