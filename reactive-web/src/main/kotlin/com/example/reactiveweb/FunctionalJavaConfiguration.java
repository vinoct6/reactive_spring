package com.example.reactiveweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class FunctionalJavaConfiguration {


    @Bean
    public RouterFunction<ServerResponse> routes() {
        Flux<Greeting> greetingFlux = Flux.<Greeting>generate(sink -> sink.next(new Greeting("Vinoth + " + Instant.now().toString())))
                .delayElements(Duration.ofSeconds(1));


        return route(GET("/frp/greetings"), request -> ServerResponse.ok().body(Flux.just("Hello vinoth kumar"), String.class))
                .andRoute(GET("/frp/sse"), request -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_EVENT_STREAM).body(greetingFlux, Greeting.class));

    }
}
