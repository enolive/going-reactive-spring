package de.welcz.givehugsbackend;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface HugsRepository extends ReactiveMongoRepository<HugDocument, ObjectId> {
  Flux<HugDocument> findAllByOrderByTimeStampDesc();

  @Tailable
  Flux<HugDocument> findAllBy();
}
