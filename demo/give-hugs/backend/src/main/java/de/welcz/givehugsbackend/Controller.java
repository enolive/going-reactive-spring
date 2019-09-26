package de.welcz.givehugsbackend;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@CrossOrigin
public class Controller {
  private final HugsRepository repository;

  @Autowired
  public Controller(HugsRepository repository) {
    this.repository = repository;
  }

  @PostMapping("/hugs")
  public Mono<HugDocument> insertNewHug(@RequestBody Mono<InsertHugDto> hug) {
    return hug.map(this::transformToDocument)
              .flatMap(repository::save);
  }

  @GetMapping("/hugs")
  public Flux<HugDocument> getAllHugs() {
    return repository.findAllByOrderByTimeStampDesc();
  }

  @GetMapping(value = "/hugs/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<HugDocument> streamAllHugs() {
    return repository.findAllBy();
  }

  private HugDocument transformToDocument(InsertHugDto insertHugDto) {
    return new HugDocument(ObjectId.get(),
                           insertHugDto.getFrom(),
                           LocalDateTime.now());
  }
}
