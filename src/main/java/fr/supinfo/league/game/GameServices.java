package fr.supinfo.league.game;

import fr.supinfo.league.game.comment.CommentDto;
import fr.supinfo.league.game.comment.CommentEntity;
import fr.supinfo.league.game.comment.CommentRepository;
import fr.supinfo.league.game.event.EventDto;
import fr.supinfo.league.game.event.EventEntity;
import fr.supinfo.league.game.event.EventMapper;
import fr.supinfo.league.game.event.EventRepository;
import fr.supinfo.league.season.matchday.MatchDayServices;
import fr.supinfo.league.team.TeamServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class GameServices {
    private final GameRepository gameRepository;
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final GameMapper gameMapper;

    private final MatchDayServices matchDayServices;
    private final TeamServices teamServices;

    public List<GameDto> getGames(LocalDate date) {
        List<GameDto> result;
        if (Objects.isNull(date)) {
            result = this.gameMapper.entityToDto(this.gameRepository.findAll());
        } else {
            Optional<UUID> matchDayId = this.matchDayServices.retrieveMatchDayId(date);
            if (matchDayId.isPresent()) {
                result = this.gameMapper.entityToDto(this.gameRepository.findByMatchDayId(matchDayId.get()));
            } else {
                result = Collections.emptyList();
            }
        }
        return result;
    }

    public GameDto createGame(GameDto game) {
        // checks
        this.matchDayServices.checkMatchDayToCreateGame(game.matchDayId());
        this.teamServices.checkTeams(game.homeTeamId(), game.visitorTeamId());

        GameEntity gameEntity = this.gameMapper.dtoToEntity(game);
        GameEntity saved = this.gameRepository.save(gameEntity);
        return this.gameMapper.entityToDto(saved);
    }

    public GameDto reportGame(UUID gameId, String reason) {
        GameEntity game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        if (game.hasStarted()) {
            throw new IllegalStateException("Game has already started, cannot be postponed");
        }

        game.setPostponed(true);
        game.setPostponedReason(reason);
        gameRepository.save(game);

        return gameMapper.entityToDto(game);
    }

    public GameDto suspendGame(UUID gameId, String reason) {
        GameEntity game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        if (!game.hasStarted()) {
            throw new IllegalStateException("Game has not started, cannot be suspended");
        }

        game.setSuspended(true);
        game.setSuspendReason(reason);
        gameRepository.save(game);

        return gameMapper.entityToDto(game);
    }

    public GameDto selectStartTime(UUID gameId, LocalTime newTime) {
        GameEntity game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        LocalTime endTime = game.getEndTime();

        // Vérifie si l'heure de fin est avant la nouvelle heure de début
        if (endTime != null && endTime.isBefore(newTime)) {
            throw new IllegalArgumentException("Invalid Start Time value");
        }

        game.setStartTime(newTime);
        gameRepository.save(game);

        return gameMapper.entityToDto(game);
    }

    public GameDto selectEndTime(UUID gameId, LocalTime newTime) {
        GameEntity game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        LocalTime startTime = game.getStartTime();

        // Vérifie si l'heure de début est après la nouvelle heure de fin
        if (startTime != null && newTime.isBefore(startTime)) {
            throw new IllegalArgumentException("Invalid Start Time value");
        }

        game.setEndTime(newTime);
        gameRepository.save(game);

        return gameMapper.entityToDto(game);
    }

    public CommentDto addCommentToGame(UUID gameId, CommentDto commentDto) {
        Optional<GameEntity> optionalGame = gameRepository.findById(gameId);

        if (optionalGame.isPresent()) {
            GameEntity game = optionalGame.get();
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setContent(commentDto.content());
            commentEntity.setAuthor(commentDto.author());
            commentEntity.setGame(game);

            commentEntity = commentRepository.save(commentEntity);
            game.getComments().add(commentEntity);
            gameRepository.save(game);

            return GameMapper.toCommentDto(commentEntity);
        } else {
            throw new RuntimeException("Game not found with ID: " + gameId);
        }
    }

    public EventDto addEventToGame(UUID gameId, EventDto eventDto) {
        GameEntity game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        EventEntity eventEntity = eventMapper.dtoToEntity(eventDto);
        eventEntity.setGame(game);

        eventEntity = eventRepository.save(eventEntity);

        return eventMapper.entityToDto(eventEntity);
    }
}
