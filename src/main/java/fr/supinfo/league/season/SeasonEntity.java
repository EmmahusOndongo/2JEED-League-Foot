package fr.supinfo.league.season;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class SeasonEntity {
    @Id
    private UUID id;
    @Column(nullable = false, unique = true)
    private String label;
}
