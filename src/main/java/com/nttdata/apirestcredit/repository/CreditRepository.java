package com.nttdata.apirestcredit.repository;

import com.nttdata.apirestcredit.model.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CreditRepository extends ReactiveMongoRepository<Credit, String> {

    Mono<Credit> findByContractNumber(String contractNumber);
}
