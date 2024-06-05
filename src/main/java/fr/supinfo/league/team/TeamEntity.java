package fr.supinfo.league.team;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class TeamEntity {
    @Id
    private UUID id;
    private String name;
    private String description;
}
