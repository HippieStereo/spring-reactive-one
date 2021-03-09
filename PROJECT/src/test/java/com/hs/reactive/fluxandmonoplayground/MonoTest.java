package com.hs.reactive.fluxandmonoplayground;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {
	
	@Test
	public void monoTest() {
		
		System.out.println("\nTEST START - \"monoTest()\"\n");
		
		Mono<String> stringMono = Mono.just("Spring");
		
		StepVerifier.create(stringMono.log())
			.expectNext("Spring")
			.verifyComplete();
		
		System.out.println("\nTEST END - \"monoTest()\"\n");
		
	}
	
	@Test
	public void monoTest_Error() {
		
		System.out.println("\nTEST START - \"monoTest_Error()\"\n");
		
		StepVerifier.create(Mono.error(new RuntimeException("Exception occurred !!!")).log())
			/*.expectError(RuntimeException.class)*/
			.expectErrorMessage("Exception occurred !!!")
			.verify();
		
		System.out.println("\nTEST END - \"monoTest_Error()\"\n");
		
	}

}
