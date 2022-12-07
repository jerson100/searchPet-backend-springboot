package pe.com.searchpet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication()
@EnableMongoAuditing
public class SearchPetApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchPetApplication.class, args);
    }

}
