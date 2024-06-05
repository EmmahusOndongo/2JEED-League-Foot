package fr.supinfo.league;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(
        title = "Gestion de league de football",
        version = "0.0.1",
        description = "Une API pour gérer les saisons et les matchs de football ...",
        license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"),
        contact = @Contact(url = "http://supinfo.com", name = "Sammy", email = "sammy@supinfo.com")
)
)
public class LeagueApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeagueApplication.class, args);
    }
    
}
