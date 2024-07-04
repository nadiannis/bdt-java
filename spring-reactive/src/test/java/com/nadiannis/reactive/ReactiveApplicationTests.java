package com.nadiannis.reactive;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
class ReactiveApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testMonoAndFlux() {
		Mono<Integer> mono1 = Mono.just(26);
		mono1.subscribe(value -> System.out.println(value));  // 26

		Mono<String> mono2 = Mono.just("Spring WebFlux");
		mono2.subscribe(value -> System.out.println(value));  // Sping WebFlux

		Flux<Integer> flux = Flux.range(1, 5);
		flux.subscribe(
				value -> System.out.println("Received: " + value),
				error -> System.out.println("Error: " + error),
				() -> System.out.println("Completed")
		);
	}

	@Test
	public void testMapOperator() {
		Flux<Integer> originalFlux = Flux.just(1, 2, 3);
		Flux<String> mappedFlux = originalFlux.map(number -> "Number: " + number);

		System.out.println(originalFlux);
		System.out.println(mappedFlux);
		originalFlux.subscribe(value -> System.out.println(value));
		mappedFlux.subscribe(value -> System.out.println(value));
	}

	@Test
	public void testFilterOperator() {
		Flux<Integer> numbers = Flux.range(1, 10);
		Flux<Integer> evenNumbers = numbers.filter(number -> number % 2 == 0);

		System.out.println(numbers);
		System.out.println(evenNumbers);
		numbers.subscribe(value -> System.out.println(value));
		evenNumbers.subscribe(value -> System.out.println(value));
	}

	@Test
	public void testFlapMapOperator() {
		Flux<Integer> numbers = Flux.just(1, 2, 3);
		Flux<String> letterFlux = numbers.flatMap(number -> Flux.just("A", "B").map(letter -> number + letter));

		System.out.println(numbers);
		System.out.println(letterFlux);
		numbers.subscribe(value -> System.out.println(value));
		letterFlux.subscribe(value -> System.out.println(value));
	}

	@Test
	public void testOperatorsChaining() {
		Flux<Integer> numbers = Flux.just(1, 2, 3, 4, 5);
		Flux<String> transformedFlux = numbers
				.filter(number -> number % 2 == 0)
				.map(number -> "Even: " + number);

		System.out.println(numbers);
		System.out.println(transformedFlux);
		numbers.subscribe(value -> System.out.println(value));
		transformedFlux.subscribe(value -> System.out.println(value));
	}

}
