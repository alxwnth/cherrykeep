package my.nxvembrx.cherrybox.cherrykeep.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

/*    @Bean
    CommandLineRunner initDatabase(NoteRepository repository) {
        return args -> {
            log.info("Preloading {}", repository.save(new Note("test", )));
        };
    }*/
}
