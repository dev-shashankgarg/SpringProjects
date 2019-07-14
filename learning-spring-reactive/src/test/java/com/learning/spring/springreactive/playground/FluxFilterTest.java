package com.learning.spring.springreactive.playground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxFilterTest {

  @Test
  public void testFluxWithFilter(){

    Flux<String> stringFlux = Flux.just("aa","aab","bb","ab").filter(s -> !s.startsWith("aa")).repeat(2);

    StepVerifier.create(stringFlux).expectNext("bb","ab","bb","ab","bb","ab").verifyComplete();

  }

}
