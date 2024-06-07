package fr.supinfo.league.game;

import fr.supinfo.league.game.comment.CommentDto;
import fr.supinfo.league.game.event.EventDto;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/games")
@RestController
public class GameController {

    private final GameServices gameServices;

    @GetMapping
    public List<GameDto> getGames(@RequestParam(required = false, name = "date") LocalDate date) {
        return this.gameServices.getGames(date);
    }

    @RolesAllowed({"ADMIN"})
    @PostMapping
    public GameDto createGame(@RequestBody GameDto game) {
        return this.gameServices.createGame(game);
    }

    @RolesAllowed({"ADMIN", "JOURNALIST"})
    @PutMapping("/{gameId}/start-time")
    public GameDto selectStartTime(@PathVariable UUID gameId, @RequestBody LocalTime newTime) {
        return this.gameServices.selectStartTime(gameId, newTime);
    }

    @RolesAllowed({"ADMIN", "JOURNALIST"})
    @PutMapping("/{gameId}/end-time")
    public GameDto selectEndTime(@PathVariable UUID gameId, @RequestBody LocalTime newTime) {
        return this.gameServices.selectEndTime(gameId, newTime);
    }

    @RolesAllowed({"ADMIN", "JOURNALIST"})
    @PostMapping("/{gameId}/comments")
    public CommentDto addCommentToGame(@PathVariable UUID gameId, @RequestBody CommentDto commentDto) {
        return this.gameServices.addCommentToGame(gameId, commentDto);
    }

    @RolesAllowed({"ADMIN", "JOURNALIST"})
    @PostMapping("/{gameId}/events")
    public EventDto addEventToGame(@PathVariable UUID gameId, @RequestBody EventDto eventDto) {
        return this.gameServices.addEventToGame(gameId, eventDto);
    }
}
