package com.learning.spring.springreactive.playground;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoFactoryTest {

  private List<String> alphabets = Arrays.asList("a", "b", "c");
  private String[] alphabetArray = new String[]{"d", "e", "f"};
  private Stream<String> alphabetStream = alphabets.stream();

  @Test
  public void testFluxIterable() {

    Flux<String> stringFlux = Flux.fromIterable(alphabets);

    StepVerifier.create(stringFlux).expectNext("a", "b", "c").verifyComplete();

    Flux<String> stringFlux1 = Flux.fromArray(alphabetArray);

    StepVerifier.create(stringFlux1).expectNext("d", "e", "f").verifyComplete();

    Flux<String> stringFlux2 = Flux.fromStream(alphabetStream).log();

    StepVerifier.create(stringFlux2).expectNext("a", "b", "c").verifyComplete();

  }

  @Test
  public void testMonoFactory() {

    Mono<Object> objectMono = Mono.justOrEmpty(null).log();

    StepVerifier.create(objectMono).verifyComplete();

    Supplier<String> stringSupplier = () -> "abc";

    Mono<String> stringMono = Mono.fromSupplier(stringSupplier);

    StepVerifier.create(stringMono).expectNext("abc").verifyComplete();

  }

  @Test
  public void testFluxRange() {

    Flux<Integer> integerFlux = Flux.range(2, 10);

    StepVerifier.create(integerFlux.log()).expectNext(2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
        .verifyComplete();

  }

}
