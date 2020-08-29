package de.welcz.givehugsbackend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@CrossOrigin()
public class Controller {
  private final HugsRepository repository;

  @Autowired
  public Controller(HugsRepository repository) {
    this.repository = repository;
  }

  @Operation(summary = "gives a person some hugs",
             requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                 content = @Content(schema = @Schema(implementation = InsertHugDto.class))
             ))
  @PostMapping(value = "/hugs",
               consumes = MediaType.APPLICATION_JSON_VALUE,
               produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<HugDocument> insertNewHug(@RequestBody Mono<InsertHugDto> hug) {
    return hug.map(this::transformToDocument)
              .flatMap(repository::save);
  }

  @Operation(summary = "gets a list of currently available hugs")
  @GetMapping(value = "/hugs", produces = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_STREAM_JSON_VALUE,
      MediaType.TEXT_EVENT_STREAM_VALUE
  })
  public Flux<HugDocument> getAllHugs() {
    return repository.findAllByOrderByTimeStampDesc();
  }

  @Operation(summary = "gets an infinite stream of hugs")
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
