package com.example.reactiveweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux

@SpringBootApplication
class ReactiveWebApplication {

    @Bean
    fun routes() = router {

        GET("/frp/kotlin/hello") {
            ServerResponse.ok().body(Flux.just("hello"), String::class.java)
        }

    }
}

fun main(args: Array<String>) {
    runApplication<ReactiveWebApplication>(*args)
}
