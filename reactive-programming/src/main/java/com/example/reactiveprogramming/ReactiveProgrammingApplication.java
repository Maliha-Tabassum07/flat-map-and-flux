package com.example.reactiveprogramming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ReactiveProgrammingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveProgrammingApplication.class, args);

		Flux<Integer> evenNumbers = Flux
				.range(4, 8)
				.filter(x -> x % 2 == 0);

		Flux<Integer> oddNumbers = Flux
				.range(4, 8)
				.filter(x -> x % 2 > 0);
//		Mono<String> combinedMono = evenNumbers.concatWith(oddNumbers)
//				.map(Object::toString)
//				.collectList()
//				.map(list -> String.join(" ", list));
//		combinedMono.subscribe(System.out::println);
//		Mono<Integer> combinedMono1 = evenNumbers.mergeWith(oddNumbers)
//				.last();
//		combinedMono1.subscribe(System.out::println);

		//Scenarios of flat map
		//Find product of a list
		Mono<Integer> printNum = evenNumbers.concatWith(oddNumbers)
				.collectList()
				.flatMap(list -> Mono.fromSupplier(() -> list.get(3)));
		printNum.subscribe(System.out::println);
		Mono<String> printList = evenNumbers.concatWith(oddNumbers)
				.collectList()
				.flatMap(number -> Mono.just("Number:" + number));
		printList.subscribe(System.out::println);

		Mono<String> printList2 = evenNumbers.concatWith(oddNumbers)
				.flatMap(number -> Mono.just("Number:" + number))
				.collectList()
				.map(list -> String.join(" ", list));
		printList2.subscribe(System.out::println);
		Mono<Integer> productOfList = evenNumbers.concatWith(oddNumbers)
				.collectList()
				.flatMap(list -> Mono.justOrEmpty(list.stream().reduce((a, b) -> a * b)));
		productOfList.subscribe(System.out::println);

		//Task2
		Flux<String> flux1 = Flux.just("Hello,","Welcome To");
		Flux<String> flux2 = Flux.just("A","new World!");

		Mono<String> concatString = flux1.concatWith(flux2)
				.map(String::toUpperCase)
				.collectList()
				.map(list -> String.join(" ", list));
		concatString.subscribe(System.out::print);
		System.out.println();
		Mono<String> mergeString = flux1.mergeWith(flux2)
				.map(String::toUpperCase)
				.collectList()
				.map(list -> String.join(" ", list));
		mergeString.subscribe(System.out::print);
		System.out.println();
		Mono<String> zipString = flux1.zipWith(flux2, (str1, str2) -> str1 + " " + str2)
				.map(String::toUpperCase)
				.collectList()
				.map(list -> String.join(" ", list));
		zipString.subscribe(System.out::print);

	}

}
