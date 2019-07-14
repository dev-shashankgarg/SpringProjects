package com.learning.spring.springreactive.playground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

  @Test
  public void fluxTest() {

    Flux<String> stringFlux = Flux.just("spring", "spring boot", "reactive").log();
    stringFlux.subscribe(System.out::println, null, () -> System.out.println("Completed"));
  }

  @Test
  public void fluxElementsTest() {

    Flux<String> stringFlux = Flux.just("spring", "spring boot", "reactive").log();
    stringFlux.subscribe(System.out::println, null, () -> System.out.println("Completed"));

    StepVerifier.create(stringFlux)
        .expectNext("spring", "spring boot", "reactive")
        .verifyComplete();

  }

  @Test
  public void fluxTestException() {

    Flux<String> stringFlux = Flux.just("spring", "spring boot", "reactive")
        .concatWith(Flux.error(new RuntimeException("Testing exception from flux")))
        .log();
    stringFlux.subscribe(System.out::println, System.err::println);

  }


  @Test
  public void fluxElementsTestException() {

    Flux<String> stringFlux = Flux.just("spring", "spring boot", "reactive")
        .concatWith(Flux.error(new RuntimeException("Testing exception from flux")))
        .log();
    stringFlux.subscribe(System.out::println, System.err::println);

    StepVerifier.create(stringFlux)
        .expectNext("spring", "spring boot", "reactive")
        .expectError(RuntimeException.class)
        .verify();

  }

  @Test
  public void monoElementTest() {

    Mono<String> monoFlux = Mono.just("spring").log();
    monoFlux.subscribe(System.out::println, null, () -> System.out.println("Completed"));

    StepVerifier.create(monoFlux)
        .expectNext("spring")
        .verifyComplete();

  }

  @Test
  public void monoElementTestException() {

    StepVerifier.create(Mono.error(RuntimeException::new))
        .expectError(RuntimeException.class)
        .verify();

  }


}
