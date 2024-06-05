package fr.supinfo.league.season.matchday;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MatchDayMapper {
    List<MatchDayDto> entityToDto(List<MatchDayEntity> entityList);

    MatchDayDto entityToDto(MatchDayEntity entity);

    MatchDayEntity dtoToEntity(MatchDayDto matchDay);
}
