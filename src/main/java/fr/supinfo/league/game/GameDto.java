package fr.supinfo.league.game;

import fr.supinfo.league.game.comment.CommentDto;
import fr.supinfo.league.game.event.EventDto;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record GameDto(UUID id, String description, @NotNull UUID matchDayId, @NotNull UUID homeTeamId,
                      @NotNull UUID visitorTeamId, @NotNull LocalTime startTime, @NotNull LocalTime endTime,
                      List<CommentDto> comments, List<EventDto> events) {
}
