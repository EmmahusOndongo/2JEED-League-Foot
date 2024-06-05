package fr.supinfo.league.season.matchday;

import fr.supinfo.league.season.SeasonEntity;
import fr.supinfo.league.season.SeasonRepository;
import org.assertj.core.api.Assertions;
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

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.UUID;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class MatchDayControllerTests {

    private static final String TESTED_URL_PREFIX = "/seasons/";
    private static final String TESTED_URL_SUFFIX = "/days";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private MatchDayRepository matchDayRepository;

    @WithMockUser
    @Test
    void whenCreateMatchDay_givenExistingSeason() throws Exception {
        // Given
        SeasonEntity season = new SeasonEntity();
        season.setLabel("Season to complete");
        String seasonIdentifier = "8a0dfcf5-6d5a-4b03-b640-583a9af15202";
        UUID uuid = UUID.fromString(seasonIdentifier);
        season.setId(uuid);
        this.seasonRepository.save(season);
        Path input = Path.of("src", "test", "resources", "inputs", "matchday-creation.json");
        String body = Files.readString(input);


        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(TESTED_URL_PREFIX + seasonIdentifier + TESTED_URL_SUFFIX)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        String expected = Files.readString(Path.of("src", "test", "resources", "expectations", "created-matchday.json"));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @WithMockUser
    @Test
    void whenCreateMatchDay_givenSameDate() throws Exception {
        // Given
        SeasonEntity season = new SeasonEntity();
        season.setLabel("Season to complete");
        String seasonIdentifier = "8a0dfcf5-6d5a-4b03-b640-583a9af15202";
        UUID uuid = UUID.fromString(seasonIdentifier);
        season.setId(uuid);
        this.seasonRepository.save(season);

        MatchDayEntity dayOne = new MatchDayEntity();
        String dayOneIdentifier = "c53f55c3-108f-4c73-8fb2-c72775550ba7";
        dayOne.setId(UUID.fromString(dayOneIdentifier));
        dayOne.setSeasonId(season.getId());
        dayOne.setDate(LocalDate.parse("2021-12-24"));
        this.matchDayRepository.save(dayOne);

        Path input = Path.of("src", "test", "resources", "inputs", "matchday-creation.json");
        String body = Files.readString(input);


        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(TESTED_URL_PREFIX + seasonIdentifier + TESTED_URL_SUFFIX)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("MatchDay already exists !"));
    }

    @WithMockUser
    @Test
    void whenRetrieveMatchDays() throws Exception {
        // Given
        SeasonEntity season = new SeasonEntity();
        season.setLabel("Season with dates");
        String seasonIdentifier = "8a0dfcf5-6d5a-4b03-b640-583a9af15202";
        UUID uuid = UUID.fromString(seasonIdentifier);
        season.setId(uuid);
        this.seasonRepository.save(season);

        MatchDayEntity dayOne = new MatchDayEntity();
        String dayOneIdentifier = "c53f55c3-108f-4c73-8fb2-c72775550ba7";
        dayOne.setId(UUID.fromString(dayOneIdentifier));
        dayOne.setSeasonId(season.getId());
        dayOne.setDate(LocalDate.parse("2021-02-01"));
        this.matchDayRepository.save(dayOne);

        MatchDayEntity dayTwo = new MatchDayEntity();
        String dayTwoIdentifier = "e87c9845-5afb-4cba-9922-3dd260f45e4e";
        dayTwo.setId(UUID.fromString(dayTwoIdentifier));
        dayTwo.setSeasonId(season.getId());
        dayTwo.setDate(LocalDate.parse("2021-03-02"));
        this.matchDayRepository.save(dayTwo);

        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get(TESTED_URL_PREFIX + seasonIdentifier + TESTED_URL_SUFFIX));

        // Then
        String expected = Files.readString(Path.of("src", "test", "resources", "expectations", "retrieved-matchdays.json"));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @WithMockUser
    @Test
    void whenDeletingMatchDay() throws Exception {
        // Given
        SeasonEntity season = new SeasonEntity();
        season.setLabel("Season with dates");
        String seasonIdentifier = "8a0dfcf5-6d5a-4b03-b640-583a9af15202";
        UUID uuid = UUID.fromString(seasonIdentifier);
        season.setId(uuid);
        this.seasonRepository.save(season);

        MatchDayEntity dayOne = new MatchDayEntity();
        String dayOneIdentifier = "c53f55c3-108f-4c73-8fb2-c72775550ba7";
        dayOne.setId(UUID.fromString(dayOneIdentifier));
        dayOne.setSeasonId(season.getId());
        dayOne.setDate(LocalDate.parse("2021-02-01"));
        this.matchDayRepository.save(dayOne);

        int originalSize = this.matchDayRepository.findAll().size();

        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.delete(TESTED_URL_PREFIX + seasonIdentifier + TESTED_URL_SUFFIX + "/" + dayOneIdentifier));

        // Then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertThat(originalSize).isEqualTo(1);
        int expectedSize = this.matchDayRepository.findAll().size();
        Assertions.assertThat(expectedSize).isEqualTo(0);
    }
    
}
