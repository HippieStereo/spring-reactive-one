package com.hs.reactive.fluxandmonoplayground;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

public class FluxAndMonoTest {
	
	@Test
	public void fluxTest() {
		
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Execption Occurred")))
				.concatWith(Flux.just("After error is this added ?"))
				.log();
		
		stringFlux.subscribe(System.out::println, (e) -> System.err.println("Eception is : " + e)
				,() -> System.out.println("Completed"));
		
	}

}
