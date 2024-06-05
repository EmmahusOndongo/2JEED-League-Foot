package fr.supinfo.league.season;

import fr.supinfo.league.season.matchday.MatchDayDto;
import fr.supinfo.league.season.matchday.MatchDayServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/seasons")
@RestController
@Slf4j
public class SeasonController {

    private final SeasonServices seasonServices;
    private final MatchDayServices matchDayServices;

    @Operation(summary = "Un point d'entrée pour créer une saison de foot, le label est obligatoire et doit être unique. Pas de duplications possible.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Création d'une saison de foot.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SeasonDto.class))}),
            @ApiResponse(responseCode = "400", description = "Une saison avec le même label existe déjà.",
                    content = @Content)})
    @PostMapping
    public @ResponseBody SeasonDto createSeason(@RequestBody SeasonDto season) {
        return this.seasonServices.createSeason(season);
    }

    @DeleteMapping("/{id}")
    public void deleteSeason(@PathVariable(name = "id") String seasonId) throws SeasonNotEmptyException {
        if (StringUtils.isEmpty(seasonId)) {
            throw new IllegalArgumentException("L'identifiant de la saison à supprimer est obligatoire.");
        }
        // check du type de l'identifiant (format UUID attendu)
        UUID seasonUuid = UUID.fromString(seasonId);
        this.seasonServices.deleteSeason(seasonUuid);
    }


    /*
    Exemple d'API REST en utilisant la notion de resource et sous-resource.
    Nous pouvons considérer que le lien est fort entre une saison et les journées d'une saison.
    Ou en prenant le point de vue du front, cela n'a pas de sens d'afficher une journée sans connaitre la saison auquelle elle appartient.

    Au contraire de la liste des matchs qui peut être affiché indépendamment d'une saison, par exemple en filtrant sur une équipe pour voir tout ses matchs sur l'ensemble des saisons.
     */
    @Operation(summary = "Un point d'entrée pour récupérer les journées dans une saison de foot.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tableau des journées dans une saison de foot.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MatchDayDto.class))})})
    @GetMapping("/{seasonId}/days")
    public @ResponseBody List<MatchDayDto> createMatchDay(@PathVariable(name = "seasonId") String seasonId) {
        if (StringUtils.isEmpty(seasonId)) {
            throw new IllegalArgumentException("L'identifiant de la saison est obligatoire pour pouvoir ajouter une journée.");
        }
        // check du type de l'identifiant (format UUID attendu)
        UUID seasonUuid = UUID.fromString(seasonId);
        return this.matchDayServices.getMatchDayList(seasonUuid);
    }

    @Operation(summary = "Un point d'entrée pour créer une journée dans une saison de foot, la date est obligatoire.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Création d'une journée dans une saison de foot.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MatchDayDto.class))}),
            @ApiResponse(responseCode = "400", description = "Une journée avec la même date existe déjà.",
                    content = @Content)})
    @PostMapping("/{seasonId}/days")
    public @ResponseBody MatchDayDto createMatchDay(@PathVariable(name = "seasonId") String seasonId, @RequestBody MatchDayDto matchDay) {
        if (StringUtils.isEmpty(seasonId)) {
            throw new IllegalArgumentException("L'identifiant de la saison est obligatoire pour pouvoir ajouter une journée.");
        }
        // check du type de l'identifiant (format UUID attendu)
        UUID seasonUuid = UUID.fromString(seasonId);
        return this.matchDayServices.createMatchDay(seasonUuid, matchDay);
    }

    @DeleteMapping("/{seasonId}/days/{id}")
    public void deleteMatchDay(@PathVariable(name = "seasonId") String seasonId, @PathVariable(name = "id") String matchDayId) {
        if (StringUtils.isEmpty(seasonId)) {
            throw new IllegalArgumentException("L'identifiant de la saison à supprimer est obligatoire.");
        }
        // check du type de l'identifiant (format UUID attendu)
        UUID seasonUuid = UUID.fromString(seasonId);
        UUID idMatchDay = UUID.fromString(matchDayId);
        log.debug("Deletion of day {} in season {}", idMatchDay, seasonUuid);
        this.matchDayServices.deleteMatchDay(idMatchDay);
    }
}
