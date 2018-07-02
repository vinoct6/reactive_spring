package com.example.reactiveweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.function.Consumer;

@Configuration
public class WebsocketConfiguration {

    @Bean
    public WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public WebSocketHandler webSocketHandler(){
        return session -> {
            Flux<WebSocketMessage> webSocketMessageFlux = Flux.
                    <Greeting>generate(g -> g.next(new Greeting("Hello Vinoth @ " + Instant.now())))
                    .map(g -> session.textMessage(g.getText()))
                    .delayElements(Duration.ofSeconds(1))
                    .doFinally(signalType -> System.out.println("Good bye"));

            return session.send(webSocketMessageFlux);
        };
    }

    @Bean
    HandlerMapping handlerMapping() {
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        simpleUrlHandlerMapping.setUrlMap(Collections.singletonMap("/ws/hello", webSocketHandler()));
        simpleUrlHandlerMapping.setOrder(10);
        return simpleUrlHandlerMapping;

    }
}
