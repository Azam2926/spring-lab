package com.example.lab.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
public class SSEController {

//    @GetMapping("/events")
//    @CrossOrigin(origins = "*")
//    public ResponseEntity<SseEmitter> streamEvents(HttpServerResponse response) {
//        SseEmitter emitter = new SseEmitter(response);
//
//        new Thread(() -> {
//            try {
//                for (int i = 0; i < 10; i++) {
//                    emitter.send(SseEmitter.event().data("Event " + i));
//                    Thread.sleep(1000);
//                }
//                emitter.complete();
//            } catch (Exception e) {
//                emitter.completeWithError(e);
//            }
//        }).start();
//
//        return ResponseEntity.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(emitter);
//    }

    @GetMapping(path = "/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux() {
        return Flux.interval(Duration.ofSeconds(5))
                .map(sequence -> {
                    log.info(String.valueOf(sequence));
                    return "Flux - " + LocalTime.now();
                });
    }

    @GetMapping("/stream-sse")
    public Flux<ServerSentEvent<String>> streamEvents() {
        return Flux.interval(Duration.ofSeconds(5))
                .map(sequence -> ServerSentEvent.<String> builder()
                        .id(String.valueOf(sequence))
                        .event("periodic-event")
                        .data("SSE - " + LocalTime.now())
                        .build());
    }

}
