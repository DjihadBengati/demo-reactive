package com.reactive.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON_VALUE;

@RestController
public class FluxAndMonoController {
    @GetMapping("/flux")
    public Flux<Integer> returnFlux() {
        return Flux.just(1, 2, 3)
                .delayElements(Duration.ofSeconds(1))
                .doOnComplete(() -> System.out.println("flux"))
                .log();
    }

    @GetMapping(value = "/v1/flux-stream", produces = APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> returnFluxStreamV1() {
        return Flux.just(1, 2, 3)
                .delayElements(Duration.ofSeconds(1))
                .doOnComplete(() -> System.out.println("flux-stream v1"))
                .log();
    }

    @GetMapping(value = "/v2/flux-stream", produces = APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> returnFluxStreamV2() {
        return Flux.interval(Duration.ofSeconds(1))
                .doOnCancel(() -> System.out.println("flux-stream v2"))
                .log();
    }

    @GetMapping("/mono")
    public Mono<Integer> returnMono() {
        return Mono.just(1).log();
    }
}
