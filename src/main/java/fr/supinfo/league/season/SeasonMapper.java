package fr.supinfo.league.season;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeasonMapper {

    SeasonDto entityToDto(SeasonEntity seasonEntity);

    SeasonEntity dtoToEntity(SeasonDto dto);
}
