package fr.supinfo.league.game;

import fr.supinfo.league.game.comment.CommentDto;
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

    @PutMapping("/{gameId}/report")
    @RolesAllowed("MEMBER-LEAGUE")
    public GameDto reportGame(@PathVariable UUID gameId, @RequestBody ReportReasonDto reportReasonDto) {
        return this.gameServices.reportGame(gameId, reportReasonDto.reason());
    }
    @PutMapping("/{gameId}/suspend")
    @RolesAllowed("MEMBER-LEAGUE")
    public GameDto suspendGame(@PathVariable UUID gameId, @RequestBody SuspendReasonDto suspendReasonDto) {
        return this.gameServices.suspendGame(gameId, suspendReasonDto.reason());
    }
    @PutMapping("/{gameId}/start-time")
    @RolesAllowed({"ADMIN", "JOURNALIST"})
    public GameDto selectStartTime(@PathVariable UUID gameId, @RequestBody TimeDto newTimeDto) {
        return this.gameServices.selectStartTime(gameId, LocalTime.parse(newTimeDto.getNewTime()));
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
}
