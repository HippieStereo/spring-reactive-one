package com.hs.reactive.controller;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
@DirtiesContext
public class FluxAndMonoControllerTest {
	
	@Autowired
	WebTestClient webTestClient;
	
	@Test
	public void flux_approch_1() {
		
		Flux<Integer> integerFlux = webTestClient.get().uri("/flux")
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.exchange()
			.expectStatus().isOk()
			.returnResult(Integer.class)
			.getResponseBody();
		
		StepVerifier.create(integerFlux)
			.expectSubscription()
			.expectNext(1)
			.expectNext(2)
			.expectNext(3)
			.expectNext(4)
			.verifyComplete();

	}

	@Test
	public void flux_approch_2() {
		
		webTestClient.get().uri("/flux")
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBodyList(Integer.class)
			.hasSize(4);
		
	}
	
	@Test
	public void flux_approch_3() {
		
		List<Integer> expectedList = Arrays.asList(1,2,3,4); 
		
		EntityExchangeResult<List<Integer>> entityExchangeResult = webTestClient.get().uri("/flux")
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.expectBodyList(Integer.class)
		.returnResult();
	
		assertEquals(expectedList, entityExchangeResult.getResponseBody());
		
	}
	
	@Test
	public void flux_approch_4() {
		
		List<Integer> expectedList = Arrays.asList(1,2,3,4); 
		
		webTestClient.get().uri("/flux")
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.expectBodyList(Integer.class)
		.consumeWith((response) -> {
			assertEquals(expectedList, response.getResponseBody());
		});
		
	}
	
	@Test
	public void fluxStreamInfinite_1() {
		
		Flux<Long> longStreamFlux = webTestClient.get().uri("/flux-stream-infnite-long")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.returnResult(Long.class)
				.getResponseBody();
		
		StepVerifier.create(longStreamFlux)
			.expectNext(0L)
			.expectNext(1L)
			.expectNext(2L)
			.thenCancel()
			.verify();
		
	}
	
	@Test
	public void monoTest_1() {
		
		Integer expectedValue = 1;
		
		webTestClient.get().uri("/mono")
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.expectBody(Integer.class)
		.consumeWith((result) -> {
			assertEquals(expectedValue, result.getResponseBody());
		});
		
	}
	
}
