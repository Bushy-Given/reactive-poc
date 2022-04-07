package com.rank.reactive.poc;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Log4j2
@RestController
public class ProvidersController {

    private Provider provider = new Provider();

    @GetMapping("/provider/{id}")
    public Provider getProvider(@PathVariable int id) {
        simulateDelayedProcessing();
        return provider.getProvider(id);
    }


    @GetMapping("/providers")
    public List<Provider> getProviders() {
        simulateDelayedProcessing();
        return provider.getAllProviders();
    }

    @GetMapping(path = "/providers-server-stream",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<Provider> getProvidersAsStream() {

        return Flux.range(1, 5)
                .delayElements(Duration.ofSeconds(5))
                .doOnNext(id -> log.info("processing count " + id))
                .map(id -> new Provider(id, "provider" + id));
    }


    @GetMapping(path = "/providers-server-list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Provider> getProvidersAsList(){

        return IntStream.rangeClosed(1, 5)
                .peek(id -> simulateDelayedProcessing())
                .peek(id -> log.info("processing count " + id))
                .mapToObj(id -> new Provider(id, "provider" + id))
                .collect(Collectors.toList());
    }

    @PostMapping("/provider")
    public Provider addProvider(@RequestBody Provider provider) {
        simulateDelayedProcessing();
        return this.provider.addProvider(provider);
    }

    //Just to simulate a delay
    @SneakyThrows(InterruptedException.class)
    private void simulateDelayedProcessing() {
        Thread.sleep(5000); //5 seconds
    }
}
