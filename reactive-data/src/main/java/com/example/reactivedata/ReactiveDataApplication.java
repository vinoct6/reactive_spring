package com.example.reactivedata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ReactiveDataApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ReactiveDataApplication.class, args);
        Thread.sleep(1000 * 10);
    }
}

@Log
@Component
class DataPopulator implements ApplicationRunner {

    @Autowired
    ReservationRepo reservationRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        this.reservationRepo.deleteAll().thenMany(
                Flux.just("Vinoth", "Varshyth", "Mark")
                        .map(name -> new Reservation(null, name)).flatMap(this.reservationRepo::save)

        ).thenMany(this.reservationRepo.findAll()).subscribe(System.out::println);


    }
}

interface ReservationRepo extends ReactiveMongoRepository<Reservation, String> {

}

@Data
@Document
@AllArgsConstructor
class Reservation {

    @Id
    private String id;

    private String reservation;
}