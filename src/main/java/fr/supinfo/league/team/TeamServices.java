package fr.supinfo.league.team;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TeamServices {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public List<TeamDto> getAllTeams() {
        return this.teamMapper.entityToDto(this.teamRepository.findAll());
    }

    public void checkTeams(UUID homeId, UUID visitorId) {
        if (!this.teamRepository.existsById(homeId)) {
            throw new IllegalArgumentException("Invalid home team.");
        }
        if (!this.teamRepository.existsById(visitorId)) {
            throw new IllegalArgumentException("Invalid visitor team.");
        }
    }
}
