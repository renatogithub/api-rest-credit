package com.nttdata.apirestcredit.service;

import com.nttdata.apirestcredit.model.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {

    Mono<Credit> create(Credit credit);

    Mono<Credit> update(Credit credit);

    Flux<Credit> listAll();

    Mono<Credit> getById(String id);

    Mono<Credit> getByContractNumber(String contractNumber);
}
