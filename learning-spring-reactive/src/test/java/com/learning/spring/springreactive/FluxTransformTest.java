package com.learning.spring.springreactive;

import static reactor.core.scheduler.Schedulers.parallel;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTransformTest {

  @Test
  public void testFlux_map() {

    Flux<String> stringFlux = Flux.just("aa", "bb", "ab", "ba").map(s -> s.toUpperCase());
    StepVerifier.create(stringFlux).expectNext("AA", "BB", "AB", "BA").verifyComplete();

  }

  @Test
  public void testFlux_map_and_filter() {

    Flux<String> stringFlux = Flux.just("aa", "bb", "ab", "ba").map(s -> s.toUpperCase())
        .filter(s -> s.endsWith("A"));
    StepVerifier.create(stringFlux).expectNext("AA", "BA").verifyComplete();
  }

  @Test
  public void testFlux_with_flatMap() {

    List<String> names = Arrays.asList("A", "BB", "CCCC", "DDD");

    Flux<String> stringFlux = Flux.fromIterable(names).flatMap(s -> Flux.fromIterable(doDb(s)));

    StepVerifier.create(stringFlux.log()).expectNext("A", "1", "BB", "2", "CCCC", "4", "DDD", "3")
        .verifyComplete();


  }

  @Test
  public void testFlux_with_flatMap_parallel() {

    Flux<String> stringFlux = Flux.just("A", "BB", "CCCC", "DDD")
        .flatMap(s -> Flux.fromIterable(doDb(s)).subscribeOn(parallel())).map(s -> s.toLowerCase());

    StepVerifier.create(stringFlux.log()).expectNext("a", "1", "bb", "2", "cccc", "4", "ddd", "3")
        .verifyComplete();

  }

  @Test
  public void testFlux_with_flatMap_parallel_window() {

    Flux<String> stringFlux = Flux.just("A", "BB", "CCCC", "DDD").window(2)
        .flatMap(s1 -> s1.map(s -> doDb(s)).subscribeOn(parallel()))
        .flatMap(s -> Flux.fromIterable(s))
        .map(s -> s.toLowerCase());

    StepVerifier.create(stringFlux.log()).expectNextCount(8)
        .verifyComplete();

  }

  @Test
  public void testFlux_with_flatMap_parallel_window_seq() {

    Flux<String> stringFlux = Flux.just("A", "BB", "CCCC", "DDD").window(1)
        .flatMapSequential(s1 -> s1.map(s -> doDb(s)).subscribeOn(parallel()))
        .flatMap(s -> Flux.fromIterable(s))
        .map(s -> s.toLowerCase());

    StepVerifier.create(stringFlux.log()).expectNextCount(8)
        .verifyComplete();

  }


  private List<String> doDb(String s) {
    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return Arrays.asList(s, "" + s.length());
  }

}
