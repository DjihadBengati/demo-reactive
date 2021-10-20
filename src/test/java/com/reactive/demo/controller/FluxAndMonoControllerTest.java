package com.reactive.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebFluxTest
public class FluxAndMonoControllerTest {

    @Autowired
    private WebTestClient client;

    @Test
    void fluxTest() {
        Flux<Integer> result = client.get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .verifyComplete();
    }

    @Test
    void fluxStreamTest() {
        client.get()
                .uri("/v1/flux-stream")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .hasSize(3);
    }

    @Test
    void fluxStreamEntityExchangeTest() {
        EntityExchangeResult<List<Integer>> result = client.get()
                .uri("/v1/flux-stream")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .returnResult();

        assertEquals(Arrays.asList(1, 2, 3), result.getResponseBody());
    }

    @Test
    void fluxStreamConsumeTest() {
        client.get()
                .uri("/v1/flux-stream")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .consumeWith((result) -> {
                    assertEquals(Arrays.asList(1, 2, 3), result.getResponseBody());
                });
    }

    @Test
    void fluxStreamV2Test() {
        Flux<Long> result = client.get()
                .uri("/v2/flux-stream")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier.create(result)
                .expectSubscription()
                .expectNext(0L)
                .expectNext(1L)
                .expectNext(2L)
                .thenCancel()
                .verify();
    }

    @Test
    void fluxMono() {
        client.get()
                .uri("/mono")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Integer.class)
                .consumeWith((result) -> {
                    assertEquals(1, result.getResponseBody());
                });
    }
}
