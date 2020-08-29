package de.welcz.givehugsbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class CreateDb implements CommandLineRunner {
  private final ReactiveMongoOperations db;

  @Autowired
  public CreateDb(ReactiveMongoOperations db) {
    this.db = db;
  }

  @Override
  public void run(String... args) {
    db.collectionExists("hugs")
      .filter(existsNot())
      .flatMap(createCollection("hugs"))
      .log()
      .block();
  }

  private Function<Boolean, Mono<?>> createCollection(String name) {
    return ignore -> db.createCollection(name,
                                         CollectionOptions.empty()
                                                          .capped()
                                                          .size(8196)
                                                          .maxDocuments(100));
  }

  private Predicate<Boolean> existsNot() {
    return exists -> !exists;
  }

}
