package fr.supinfo.league.team;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    List<TeamDto> entityToDto(List<TeamEntity> entities);
}
