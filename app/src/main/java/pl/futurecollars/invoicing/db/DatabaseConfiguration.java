package pl.futurecollars.invoicing.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.futurecollars.invoicing.db.file.FileBasedDatabase;
import pl.futurecollars.invoicing.db.file.FileService;
import pl.futurecollars.invoicing.db.file.IdService;
import pl.futurecollars.invoicing.db.file.JsonService;
import pl.futurecollars.invoicing.db.memory.InMemoryDatabase;

@Slf4j
@Configuration
public class DatabaseConfiguration {

  @Bean
  public IdService idService(FileService fileService, @Value("${invoicing-system.database.location}") String databaseLocation,
                             @Value("${invoicing-system.database.id.file}") String idFile) throws IOException {
    Path idFilePath = Files.createTempFile(databaseLocation, idFile);
    return new IdService(idFilePath, fileService);
  }

  @Bean
  @ConditionalOnProperty(name = "invoicing-system.database", havingValue = "file")
  public Database fileBasedDatabase(IdService idService, FileService fileservice, JsonService jsonService,
                                    @Value("${invoicing-system.database.location}") String databaseLocation,
                                    @Value("${invoicing-system.database.invoices.file}") String invoicesFile) throws IOException {
    log.debug("FileBasedDatabase selected");
    Path databaseFilePath = Files.createTempFile(databaseLocation, invoicesFile);
    return new FileBasedDatabase(fileservice, jsonService, idService, databaseFilePath);
  }

  @Bean
  @ConditionalOnProperty(name = "invoicing-system.database", havingValue = "memory")
  public Database inMemoryDatabase() {
    log.debug("InMemoryDatabase selected");
    return new InMemoryDatabase();
  }
}
