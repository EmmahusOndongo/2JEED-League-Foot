package fr.supinfo.league.season;

import fr.supinfo.league.season.matchday.MatchDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SeasonServices {

    private final SeasonRepository seasonRepository;
    private final MatchDayRepository matchDayRepository;

    public SeasonDto createSeason(SeasonDto season) {
        if (this.seasonRepository.existsByLabel(season.label())) {
            throw new IllegalArgumentException("Season already exists !");
        }
        UUID id = UUID.randomUUID();
        SeasonEntity seasonEntity = new SeasonEntity();
        seasonEntity.setId(id);
        seasonEntity.setLabel(season.label());
        SeasonEntity savedSeason = this.seasonRepository.save(seasonEntity);
        return new SeasonDto(savedSeason.getId(), savedSeason.getLabel());
    }

    public void deleteSeason(UUID seasonUuid) throws SeasonNotEmptyException {
        Optional<SeasonEntity> seasonEntityOptional = this.seasonRepository.findById(seasonUuid);
        if (seasonEntityOptional.isPresent()) {
            SeasonEntity seasonEntity = seasonEntityOptional.get();
            if (this.matchDayRepository.findBySeasonId(seasonUuid).isEmpty()) {
                this.seasonRepository.delete(seasonEntity);
            } else {
                throw new SeasonNotEmptyException(seasonEntity.getLabel());
            }
        }
    }


}
