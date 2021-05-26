package com.carlsberg.consumertech.service.template;

import com.carlsberg.consumertech.core.test.testcontainers.WithTestContainers;
import com.carlsberg.consumertech.service.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "3600000")
@ActiveProfiles("test")
@Slf4j
@SuppressWarnings("initialization.fields.uninitialized")
class ProbeTests {

    static {
        WithTestContainers.mysql();
    }

    @Autowired
    WebTestClient webClient;

    @Test
    void isLive() {
        webClient.get()
                .uri("/health/live")
                .exchange()
                .expectStatus().isOk();
    }

}
