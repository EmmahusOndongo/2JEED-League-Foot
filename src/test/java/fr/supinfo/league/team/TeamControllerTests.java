package fr.supinfo.league.team;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest
@AutoConfigureMockMvc
class TeamControllerTests {

    private static final String TESTED_URL = "/teams";

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser
    @Test
    void whenRetrieveTeams() throws Exception {
        // Given

        // When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get(TESTED_URL));

        // Then
        String expected = Files.readString(Path.of("src", "test", "resources", "expectations", "teams.json"));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));

    }
}
