package fr.supinfo.league.game;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @RolesAllowed({"MEMBER-LEAGUE"})
    @PostMapping("/{id}/report")
    public GameDto reportMatch(@PathVariable UUID id, @RequestBody String reason) throws Exception {
        return this.gameServices.reportMatch(id, reason);
    }
}
