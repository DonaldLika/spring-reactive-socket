package com.playground.springreactivesocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringReactiveSocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveSocketApplication.class, args);
	}

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ExampleRequest{
	private String name;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ExampleResponse{
	private String message;

}

@Controller
class ExampleController{

	@MessageMapping("hello")
	Flux<ExampleResponse> hello(ExampleRequest exampleRequest){
		var stream= Stream.generate(()->
			new ExampleResponse("Hello there "+exampleRequest.getName()+ " @"+ Instant.now()+ " !")
		);
		return Flux
				.fromStream(stream)
				.delayElements(Duration.ofSeconds(1));
	}

}
