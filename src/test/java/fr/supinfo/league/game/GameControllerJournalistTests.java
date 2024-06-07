package fr.supinfo.league.game;

import fr.supinfo.league.season.matchday.MatchDayEntity;
import fr.supinfo.league.season.matchday.MatchDayRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerJournalistTests {

    private static final String TESTED_URL = "/games";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private MatchDayRepository matchDayRepository;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(roles = {"JOURNALIST"})
    @Test
    void selectNewStartTime_givenJournalist() throws Exception {
        // Given
        MatchDayEntity matchDay = new MatchDayEntity();
        matchDay.setId(UUID.randomUUID());
        matchDay.setDate(LocalDate.now().plusDays(5));
        this.matchDayRepository.save(matchDay);

        UUID gameId = UUID.randomUUID();
        GameEntity game = new GameEntity();
        game.setId(gameId);
        game.setDescription("Game for journalist test");
        game.setStartTime(LocalTime.of(15, 15, 15));
        game.setEndTime(LocalTime.of(18, 40, 10));
        game.setMatchDayId(matchDay.getId());
        game.setHomeTeamId(UUID.randomUUID());
        game.setVisitorTeamId(UUID.randomUUID());
        this.gameRepository.save(game);

        // Load new start time from file
        String newStartTime = Files.readString(Path.of("src", "test", "resources", "inputs", "game-start-time-update.json"));

        // Load expected response from file
        String expectedResponse = Files.readString(Path.of("src", "test", "resources", "expectations", "updated-start-time.json"));

        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.put(TESTED_URL + "/" + gameId + "/start-time")
                .content(newStartTime)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @WithMockUser(roles = {"JOURNALIST"})
    @Test
    void selectNewStartTime_givenJournalist_IncorrectStartTimeValue() throws Exception {
        // Given
        MatchDayEntity matchDay = new MatchDayEntity();
        matchDay.setId(UUID.randomUUID());
        matchDay.setDate(LocalDate.now().plusDays(5));
        this.matchDayRepository.save(matchDay);

        UUID gameId = UUID.randomUUID();
        GameEntity game = new GameEntity();
        game.setId(gameId);
        game.setDescription("Game for incorrect start time test");
        game.setStartTime(LocalTime.of(15, 15, 15));
        game.setEndTime(LocalTime.of(18, 40, 10));
        game.setMatchDayId(matchDay.getId());
        game.setHomeTeamId(UUID.randomUUID());
        game.setVisitorTeamId(UUID.randomUUID());
        this.gameRepository.save(game);

        // Load incorrect new start time from file
        String incorrectStartTime = Files.readString(Path.of("src", "test", "resources", "inputs", "incorrect-game-start-time-update.json"));

        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.put(TESTED_URL + "/" + gameId + "/start-time")
                .content(incorrectStartTime)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @WithMockUser(roles = {"JOURNALIST"})
    @Test
    void selectNewEndTime_givenJournalist() throws Exception {
        // Given
        MatchDayEntity matchDay = new MatchDayEntity();
        matchDay.setId(UUID.randomUUID());
        matchDay.setDate(LocalDate.now().plusDays(5));
        this.matchDayRepository.save(matchDay);

        UUID gameId = UUID.randomUUID();
        GameEntity game = new GameEntity();
        game.setId(gameId);
        game.setDescription("Game for journalist end time test");
        game.setStartTime(LocalTime.of(15, 15, 15));
        game.setEndTime(LocalTime.of(18, 40, 10));
        game.setMatchDayId(matchDay.getId());
        game.setHomeTeamId(UUID.randomUUID());
        game.setVisitorTeamId(UUID.randomUUID());
        this.gameRepository.save(game);

        // Load new end time from file
        String newEndTime = Files.readString(Path.of("src", "test", "resources", "inputs", "game-end-time-update.json"));

        // Load expected response from file
        String expectedResponse = Files.readString(Path.of("src", "test", "resources", "expectations", "updated-end-time.json"));

        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.put(TESTED_URL + "/" + gameId + "/end-time")
                .content(newEndTime)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @WithMockUser(roles = {"JOURNALIST"})
    @Test
    void selectNewEndTime_givenJournalist_IncorrectEndTimeValue() throws Exception {
        // Given
        MatchDayEntity matchDay = new MatchDayEntity();
        matchDay.setId(UUID.randomUUID());
        matchDay.setDate(LocalDate.now().plusDays(5));
        this.matchDayRepository.save(matchDay);

        UUID gameId = UUID.randomUUID();
        GameEntity game = new GameEntity();
        game.setId(gameId);
        game.setDescription("Game for incorrect end time test");
        game.setStartTime(LocalTime.of(15, 15, 15));
        game.setEndTime(LocalTime.of(18, 40, 10));
        game.setMatchDayId(matchDay.getId());
        game.setHomeTeamId(UUID.randomUUID());
        game.setVisitorTeamId(UUID.randomUUID());
        this.gameRepository.save(game);

        // Load incorrect new end time from file
        String incorrectEndTime = Files.readString(Path.of("src", "test", "resources", "inputs", "incorrect-game-end-time-update.json"));

        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.put(TESTED_URL + "/" + gameId + "/end-time")
                .content(incorrectEndTime)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
