package fr.supinfo.league.season.matchday;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MatchDayServices {
    private final MatchDayRepository matchDayRepository;
    private final MatchDayMapper matchDayMapper;

    public List<MatchDayDto> getMatchDayList(UUID seasonUuid) {
        List<MatchDayEntity> matchDayEntityList = this.matchDayRepository.findBySeasonId(seasonUuid);
        return this.matchDayMapper.entityToDto(matchDayEntityList);
    }

    public MatchDayDto createMatchDay(UUID seasonUuid, MatchDayDto matchDay) {
        if (this.matchDayRepository.existsBySeasonIdAndDate(seasonUuid, matchDay.date())) {
            throw new IllegalArgumentException("MatchDay already exists !");
        }
        MatchDayEntity entityToSave = this.matchDayMapper.dtoToEntity(matchDay);
        entityToSave.setSeasonId(seasonUuid);
        entityToSave.setId(UUID.randomUUID());
        MatchDayEntity saved = this.matchDayRepository.save(entityToSave);
        return this.matchDayMapper.entityToDto(saved);
    }

    public void deleteMatchDay(UUID idMatchDay) {
        this.matchDayRepository.deleteById(idMatchDay);
    }

    public void checkMatchDayToCreateGame(UUID uuid) {
        MatchDayEntity matchDayEntity = this.matchDayRepository.findById(uuid).orElseThrow();
        if (LocalDate.now().isAfter(matchDayEntity.getDate())) {
            throw new IllegalArgumentException("A game could not be created in past !");
        }
    }

    public Optional<UUID> retrieveMatchDayId(LocalDate date) {
        Optional<MatchDayEntity> matchDay = this.matchDayRepository.findByDate(date);
        return matchDay.map(MatchDayEntity::getId);
    }
}
