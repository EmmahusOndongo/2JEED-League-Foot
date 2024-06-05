package fr.supinfo.league.season;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SeasonServicesTests {

    @Mock
    private SeasonRepository repositoryMock;
    @InjectMocks
    private SeasonServices service;

    @Test
    void createSeason() {
        // Given
        SeasonDto seasonDto = new SeasonDto(null, "Saison 2018-2019");
        BDDMockito.given(this.repositoryMock.existsByLabel(BDDMockito.anyString())).willReturn(false);
        BDDMockito.given(this.repositoryMock.save(BDDMockito.any())).willAnswer(AdditionalAnswers.returnsFirstArg());

        // When
        SeasonDto createdSeason = this.service.createSeason(seasonDto);

        // Then
        Assertions.assertThat(createdSeason)
                .isNotNull()
                .extracting("label")
                .isEqualTo(seasonDto.label());
    }
}
