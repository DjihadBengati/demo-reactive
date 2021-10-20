package com.reactive.demo.playgroud;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {

    @Test
    void monoTest() {
        Mono<String> stringMono = Mono.just("Spring").log();
        StepVerifier.create(stringMono).expectNext("Spring").verifyComplete();
    }

    @Test
    void monoExceptionTest() {
        StepVerifier.create(Mono.error(new RuntimeException("Exception !")).log())
                // Only the exception class or more details like the message
                // .expectError(RuntimeException.class)
                .expectErrorMessage("Exception !")
                .verify();
    }
}
