package com.hs.reactive.fluxandmonoplayground;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {
	
	@Test
	public void fluxTest() {
		
		System.out.println("\nTEST START - \"fluxTest()\"\n");
		
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Execption Occurred")))
				.concatWith(Flux.just("After error is this added ?"))
				.log();
		
		stringFlux.subscribe(System.out::println, (e) -> System.err.println("Eception is : " + e)
				,() -> System.out.println("Completed"));
		
		System.out.println("\nTEST END - \"fluxTest()\"\n");
		
	}
	
	@Test
	public void fluxTestElements_WithoutError() {
		
		System.out.println("\nTEST START - \"fluxTestElements_WithoutError()\"\n");
		
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				.log();
		
		StepVerifier.create(stringFlux)
			.expectNext("Spring")
			.expectNext("Spring Boot")
			.expectNext("Reactive Spring")
			.verifyComplete();
		
		System.out.println("\nTEST END - \"fluxTestElements_WithoutError()\"\n");
		
	}
	
	@Test
	public void fluxTestElements_WithError() {
		
		System.out.println("\nTEST START - \"fluxTestElements_WithError()\"\n");
		
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Execption Occurred")))
				.log();
		
		StepVerifier.create(stringFlux)
			.expectNext("Spring", "Spring Boot", "Reactive Spring")
//			.expectNext("Spring")
//			.expectNext("Spring Boot")
//			.expectNext("Reactive Spring")
			.expectError(RuntimeException.class)
			/*.expectErrorMessage("Execption Occurred")*/
			.verify();
		
		System.out.println("\nTEST END - \"fluxTestElements_WithError()\"\n");
		
	}
	
	@Test
	public void fluxTestElementsCount_WithError() {
		
		System.out.println("\nTEST START - \"fluxTestElementsCount_WithError()\"\n");
		
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Execption Occurred")))
				.log();
		
		StepVerifier.create(stringFlux)
			.expectNextCount(3)
			.expectErrorMessage("Execption Occurred")
			.verify();
		
		System.out.println("\nTEST END - \"fluxTestElementsCount_WithError()\"\n");
		
	}
	
}
