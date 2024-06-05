package fr.supinfo.league.team;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class TeamInitialization implements ApplicationRunner {

    private final TeamRepository teamRepository;

    @Override
    public void run(ApplicationArguments args) {

        TeamEntity tours = new TeamEntity();
        tours.setId(UUID.fromString("22f8841b-c1c3-49e2-9e08-8884ca1ff9c0"));
        tours.setName("Tours");
        tours.setDescription("La meilleure équipe !");
        this.teamRepository.save(tours);

        TeamEntity nantes = new TeamEntity();
        nantes.setId(UUID.fromString("5b6bbd96-3b0c-4b34-aeaf-e001d0e1f0da"));
        nantes.setName("Nantes");
        this.teamRepository.save(nantes);

        TeamEntity angers = new TeamEntity();
        angers.setId(UUID.fromString("69d35007-4e6f-4799-a9df-7df6743020f3"));
        angers.setName("Angers");
        this.teamRepository.save(angers);

        TeamEntity lemans = new TeamEntity();
        lemans.setId(UUID.fromString("81138ce0-01dd-4e1b-a824-94fef238ac12"));
        lemans.setName("Le Mans");
        this.teamRepository.save(lemans);
    }
}
