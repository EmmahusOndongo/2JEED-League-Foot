package fr.supinfo.league.season;

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
import java.util.UUID;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class SeasonControllerTests {

    private static final String TESTED_URL = "/seasons";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SeasonRepository seasonRepository;

    @WithMockUser
    @Test
    void whenCreateSeason() throws Exception {
        // Given
        Path input = Path.of("src", "test", "resources", "inputs", "season-creation.json");
        String body = Files.readString(input);

        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(TESTED_URL)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        String expected = Files.readString(Path.of("src", "test", "resources", "expectations", "created-season.json"));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));

    }

    @WithMockUser
    @Test
    void whenCreateSeason_givenExistingSeason() throws Exception {
        // Given
        Path input = Path.of("src", "test", "resources", "inputs", "season-creation.json");
        String body = Files.readString(input);
        this.mockMvc.perform(MockMvcRequestBuilders.post(TESTED_URL)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(TESTED_URL)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Season already exists !"));
    }

    @WithMockUser
    @Test
    void whenDeleteSeason_givenEmptySeason() throws Exception {
        // Given
        SeasonEntity season = new SeasonEntity();
        season.setLabel("Empty season");
        UUID uuid = UUID.randomUUID();
        season.setId(uuid);
        this.seasonRepository.save(season);
        String seasonIdentifier = uuid.toString();

        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.delete(TESTED_URL + "/" + seasonIdentifier));

        // Then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithMockUser
    @Test
    void whenDeleteSeason_givenWrongIdentifier() throws Exception {
        // Given
        String seasonIdentifier = "wrong";

        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.delete(TESTED_URL + "/" + seasonIdentifier));

        // Then
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Invalid UUID string: wrong"));
    }

}
