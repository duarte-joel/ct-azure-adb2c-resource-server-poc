package com.carlsberg.consumertech.service.template;

import com.carlsberg.consumertech.service.Application;
import com.carlsberg.consumertech.service.template.brand.model.Brand;
import com.carlsberg.consumertech.core.test.testcontainers.WithTestContainers;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "3600000")
@ActiveProfiles("test")
@Slf4j
@SuppressWarnings("initialization.fields.uninitialized")
class ApplicationTests {

    static {
        WithTestContainers.mysql();
    }

    @Autowired
    DatabaseClient databaseClient;

    @Autowired
    ReactiveTransactionManager transactionManager;

    @Autowired
    WebTestClient webClient;


    @Test
    void dbFetchEntities() {
        var uuid1 = UUID.randomUUID().toString();
        var uuid2 = UUID.randomUUID().toString();
        var name1 = "Carlsberg";
        var name2 = "Super Bock";
        var expectedBrand1 = new Brand(1, uuid1, name1);
        var expectedBrand2 = new Brand(2, uuid2, name2);

        var brandFlux = prepare()
                .then(insertOne(1, uuid1, name1))
                .then(insertOne(2, uuid2, name2))
                .thenMany(databaseClient.select()
                        .from(Brand.class)
                        .fetch()
                        .all())
                .as(TransactionalOperator.create(transactionManager)::transactional);

        StepVerifier.create(brandFlux)
                .expectNext(expectedBrand1, expectedBrand2)
                .expectComplete()
                .verify();
    }

    @Test
    void getAllBrandsTest() {
        prepare()
                .then(insertOne(1, "uuid1", "Carlsberg"))
                .then(insertOne(2, "uuid2", "Super Bock"))
                .as(TransactionalOperator.create(transactionManager)::transactional)
                .block();

        webClient.get()
                .uri("/brands/uuid1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.uuid").isEqualTo("uuid1")
                .jsonPath("$.name").isEqualTo("Carlsberg");
    }

    Mono<Void> prepare() {
        return databaseClient.execute("CREATE TABLE IF NOT EXISTS brand (id INTEGER PRIMARY KEY, uuid CHAR(36), name VARCHAR(255));")
                .then()
                .then(databaseClient.execute("TRUNCATE TABLE brand;").then());
    }

    Mono<Integer> insertOne(final int id, final String uuid, final String name) {
        return databaseClient.insert()
                .into(Brand.class)
                .using(new Brand(id, uuid, name))
                .fetch()
                .rowsUpdated()
                .doOnNext(count -> log.info(String.format("Inserted %d rows into brands table.", count)));
    }
}
