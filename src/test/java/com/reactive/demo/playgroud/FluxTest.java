package com.reactive.demo.playgroud;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTest {

    @Test
    void fluxTest() {
        // New Flux with values
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.just("New flux !")).log();

        // Subscribe to Flux
        stringFlux.subscribe(System.out::println, System.err::println, () -> System.out.println("Completed"));
    }

    @Disabled("Just an example")
    @Test
    void fluxExceptionTest() {
        // New Flux with values and exception
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("This is a runtime exception")));

        // Subscribe to Flux
        stringFlux.subscribe(System.out::println, System.err::println);
    }

    @Test
    void fluxElementsWithExceptionTest() {
        /// New Flux with values and exception
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("This is a runtime exception")));

        // Create a step verifier
        StepVerifier.create(stringFlux)
                // The order is important
                .expectNext("Spring").expectNext("Spring Boot").expectNext("Reactive Spring")
                // Only the exception class or more details like the message
                // .expectError(RuntimeException.class)
                .expectErrorMessage("This is a runtime exception")
                // Only verify and not verifyComplete
                .verify();
    }

    @Test
    void fluxElementsTest() {
        // New Flux with values
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring").log();

        // Create a step verifier
        StepVerifier.create(stringFlux)
                // The order is important
                .expectNext("Spring", "Spring Boot", "Reactive Spring")
                // Or like this:
                // .expectNext("Spring").expectNext("Spring Boot").expectNext("Reactive Spring")
                // Important, this function starts the Flux
                .verifyComplete();
    }

    @Test
    void fluxElementsNumberTest() {
        // New Flux with values
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring").log();

        // Create a step verifier
        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                // Important, this function starts the Flux
                .verifyComplete();
    }
}
