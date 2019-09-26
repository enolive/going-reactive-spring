package de.welcz.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

@RestController
public class Controller {
  @GetMapping("/hello/{name}")
  public Mono<Greeting> sayHelloTo(@PathVariable String name) {
    return Mono.just(name)
               .map(this::transformToGreeting);
  }

  @GetMapping("/hellos")
  public Flux<Greeting> sayHelloTo(@RequestBody Flux<String> names) {
    return names.map(this::transformToGreeting);
  }

  private Greeting transformToGreeting(String name) {
    return new Greeting(MessageFormat.format("Hello, {0}!", name));
  }
}
