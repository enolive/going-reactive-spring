package de.welcz.givehugsbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

@Component
@Profile("bootstrap")
public class CreateDb implements CommandLineRunner {
  private final MongoOperations db;

  @Autowired
  public CreateDb(MongoOperations db) {
    this.db = db;
  }

  @Override
  public void run(String... args) {
    db.dropCollection("hugs");
    final var options = CollectionOptions.empty()
                                         .capped()
                                         .size(8196)
                                         .maxDocuments(100);
    db.createCollection("hugs", options);
  }
}
