package fr.supinfo.league.game;

import fr.supinfo.league.game.comment.CommentDto;
import fr.supinfo.league.game.event.EventDto;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
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

    // configure security roles using annotations https://www.baeldung.com/spring-security-method-security
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
    @PutMapping("/{gameId}/start-time")
    public GameDto selectEndTime(@PathVariable UUID gameId, @RequestBody LocalTime newTime) {
        return this.gameServices.selectEndTime(gameId, newTime);
    }

    @RolesAllowed({"ADMIN", "JOURNALIST"})
    @PostMapping("/{gameId}/comments")
    public ResponseEntity<CommentDto> addCommentToGame(@PathVariable UUID gameId, @RequestBody CommentDto commentDto) {
        CommentDto createdComment = gameServices.addCommentToGame(gameId, commentDto);
        return ResponseEntity.ok(createdComment);
    }

    @RolesAllowed({"ADMIN", "JOURNALIST"})
    @PostMapping("/{gameId}/events")
    public ResponseEntity<EventDto> addEventToGame(@PathVariable UUID gameId, @RequestBody EventDto eventDto) {
        EventDto createdEvent = gameServices.addEventToGame(gameId, eventDto);
        return ResponseEntity.ok(createdEvent);
    }
}
