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
public class GameControllerTests {

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

    @WithMockUser
    @Test
    void whenRetrieveGames() throws Exception {
        // Given
        MatchDayEntity matchDay = new MatchDayEntity();
        matchDay.setId(UUID.fromString("ac05477e-60e0-4c07-9455-6929c1b4c169"));
        matchDay.setDate(LocalDate.now().plusDays(5));
        this.matchDayRepository.save(matchDay);

        GameEntity game = new GameEntity();
        game.setDescription("Good game");
        game.setMatchDayId(matchDay.getId());
        game.setHomeTeamId(UUID.fromString("22f8841b-c1c3-49e2-9e08-8884ca1ff9c0"));
        game.setVisitorTeamId(UUID.fromString("5b6bbd96-3b0c-4b34-aeaf-e001d0e1f0da"));
        game.setStartTime(LocalTime.of(15, 15, 15));
        game.setEndTime(LocalTime.of(18, 40, 10));
        this.gameRepository.save(game);

        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get(TESTED_URL));

        // Then
        String expected = Files.readString(Path.of("src", "test", "resources", "expectations", "games-all.json"));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @WithMockUser
    @Test
    void whenCreateGame_givenBasicUser() throws Exception {
        // Given
        MatchDayEntity matchDay = new MatchDayEntity();
        matchDay.setId(UUID.fromString("ac05477e-60e0-4c07-9455-6929c1b4c169"));
        matchDay.setDate(LocalDate.now().plusDays(5));
        this.matchDayRepository.save(matchDay);

        Path input = Path.of("src", "test", "resources", "inputs", "game-creation.json");
        String body = Files.readString(input);

        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(TESTED_URL).content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    void whenCreateGame_givenAdminUser() throws Exception {
        // Given
        MatchDayEntity matchDay = new MatchDayEntity();
        matchDay.setId(UUID.fromString("ac05477e-60e0-4c07-9455-6929c1b4c169"));
        matchDay.setDate(LocalDate.now().plusDays(5));
        this.matchDayRepository.save(matchDay);

        Path input = Path.of("src", "test", "resources", "inputs", "game-creation.json");
        String body = Files.readString(input);

        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(TESTED_URL).content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        String expected = Files.readString(Path.of("src", "test", "resources", "expectations", "created-game.json"));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @Test
    @WithMockUser
    void whenRetrieveGames_givenFilterDate() throws Exception {
        // Given
        MatchDayEntity matchDay = new MatchDayEntity();
        matchDay.setId(UUID.fromString("ac05477e-60e0-4c07-9455-6929c1b4c169"));
        matchDay.setDate(LocalDate.now().plusDays(5));
        this.matchDayRepository.save(matchDay);

        GameEntity game = new GameEntity();
        game.setDescription("Good game");
        game.setMatchDayId(matchDay.getId());
        game.setHomeTeamId(UUID.fromString("22f8841b-c1c3-49e2-9e08-8884ca1ff9c0"));
        game.setVisitorTeamId(UUID.fromString("5b6bbd96-3b0c-4b34-aeaf-e001d0e1f0da"));
        game.setStartTime(LocalTime.of(15, 15, 15));
        game.setEndTime(LocalTime.of(21, 45, 55));
        this.gameRepository.save(game);


        MatchDayEntity otherMatchDay = new MatchDayEntity();
        otherMatchDay.setId(UUID.fromString("dd93f58d-1de0-414e-b179-35b0c2b6d4ff"));
        otherMatchDay.setDate(LocalDate.now().plusDays(10));
        this.matchDayRepository.save(otherMatchDay);

        GameEntity otherGame = new GameEntity();
        otherGame.setDescription("Good game");
        otherGame.setMatchDayId(otherMatchDay.getId());
        otherGame.setHomeTeamId(UUID.fromString("69d35007-4e6f-4799-a9df-7df6743020f3"));
        otherGame.setVisitorTeamId(UUID.fromString("81138ce0-01dd-4e1b-a824-94fef238ac12"));
        otherGame.setStartTime(LocalTime.of(18, 17, 16));
        otherGame.setEndTime(LocalTime.of(20, 30, 25));
        this.gameRepository.save(otherGame);
        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get(TESTED_URL).param("date", matchDay.getDate().toString()));

        // Then
        String expected = Files.readString(Path.of("src", "test", "resources", "expectations", "games-all.json"));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @WithMockUser(roles = {"MEMBER-LEAGUE"})
    @Test
    void whenReportMatch_givenMemberLeague() throws Exception {
        // Given
        MatchDayEntity matchDay = new MatchDayEntity();
        matchDay.setId(UUID.fromString("ac05477e-60e0-4c07-9455-6929c1b4c169"));
        matchDay.setDate(LocalDate.now().plusDays(5));
        this.matchDayRepository.save(matchDay);

        UUID gameId = UUID.fromString("ac05477e-60e0-4c07-9455-6929c1b4c169"); // Fixed UUID
        GameEntity game = new GameEntity();
        game.setId(gameId);
        game.setDescription("Game to be reported");
        game.setStartTime(LocalTime.of(17, 15, 15));
        game.setEndTime(LocalTime.of(21, 45, 55));
        game.setMatchDayId(matchDay.getId());
        game.setHomeTeamId(UUID.fromString("22f8841b-c1c3-49e2-9e08-8884ca1ff9c0"));
        game.setVisitorTeamId(UUID.fromString("5b6bbd96-3b0c-4b34-aeaf-e001d0e1f0da"));
        this.gameRepository.save(game);

        String reason = "{\"reason\":\"Inclement weather\"}";

        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.put(TESTED_URL + "/" + gameId + "/report")
                .content(reason)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isPostponed").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.postponedReason").value("Inclement weather"));
    }


    @WithMockUser(roles = {"MEMBER-LEAGUE"})
    @Test
    void whenReportMatch_givenMemberLeague_matchAlreadyStarted() throws Exception {
        // Given
        MatchDayEntity matchDay = new MatchDayEntity();
        matchDay.setId(UUID.fromString("ac05477e-60e0-4c07-9455-6929c1b4c169"));
        matchDay.setDate(LocalDate.now().plusDays(5));
        this.matchDayRepository.save(matchDay);

        UUID gameId = UUID.fromString("ac05477e-60e0-4c07-9455-6929c1b4c169"); // Generate a random UUID
        GameEntity game = new GameEntity();
        game.setId(gameId);
        game.setDescription("Game to be reported");
        game.setStartTime(LocalTime.of(17, 15, 15));
        game.setEndTime(LocalTime.of(21, 45, 55));
        game.setMatchDayId(matchDay.getId());
        game.setHomeTeamId(UUID.fromString("22f8841b-c1c3-49e2-9e08-8884ca1ff9c0"));
        game.setVisitorTeamId(UUID.fromString("5b6bbd96-3b0c-4b34-aeaf-e001d0e1f0da"));
        this.gameRepository.save(game);

        String reason = "{\"reason\":\"Inclement weather\"}";

        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.put(TESTED_URL + "/" + gameId + "/report")
                .content(reason)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @WithMockUser(roles = {"MEMBER-LEAGUE"})
    @Test
    void whenSuspendGame_givenMemberLeague() throws Exception {
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
        String reason = Files.readString(Path.of("src", "test", "resources", "inputs", "suspend-game.json"));

        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.put(TESTED_URL + "/" + gameId + "/suspend")
                .content(reason)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuspended").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.suspendReason").value("Inclement weather"));
    }

    @WithMockUser(roles = {"MEMBER-LEAGUE"})
    @Test
    void whenSuspendGame_givenMemberLeague_gameNotStarted() throws Exception {
        // Given
        MatchDayEntity matchDay = new MatchDayEntity();
        matchDay.setId(UUID.fromString("ac05477e-60e0-4c07-9455-6929c1b4c169"));
        matchDay.setDate(LocalDate.now().plusDays(5));
        this.matchDayRepository.save(matchDay);

        UUID gameId = UUID.fromString("ac05477e-60e0-4c07-9455-6929c1b4c169"); // Fixed UUID
        GameEntity game = new GameEntity();
        game.setId(gameId);
        game.setDescription("Game to be suspended");
        game.setStartTime(LocalTime.of(14, 15, 15));
        game.setEndTime(LocalTime.of(21, 45, 55));
        game.setMatchDayId(matchDay.getId());
        game.setHomeTeamId(UUID.fromString("22f8841b-c1c3-49e2-9e08-8884ca1ff9c0"));
        game.setVisitorTeamId(UUID.fromString("5b6bbd96-3b0c-4b34-aeaf-e001d0e1f0da"));
        this.gameRepository.save(game);

        String reason = "{\"reason\":\"Inclement weather\"}";

        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.put(TESTED_URL + "/" + gameId + "/suspend")
                .content(reason)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
