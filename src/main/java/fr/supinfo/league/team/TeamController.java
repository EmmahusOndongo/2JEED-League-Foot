package fr.supinfo.league.team;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/teams")
@RestController
public class TeamController {

    private final TeamServices teamServices;

    @GetMapping
    public List<TeamDto> getAllTeams() {
        return this.teamServices.getAllTeams();
    }
}
