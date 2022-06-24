/**
 * Controller that receives the requests
 *
 * @author Renato Ponce
 * @version 1.0
 * @since 2022-06-24
 */

package com.nttdata.apirestcredit.controller;

import com.nttdata.apirestcredit.model.Credit;
import com.nttdata.apirestcredit.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/credit")
public class CreditController {

    @Autowired
    private CreditService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<Credit>>> list() {
        Flux<Credit> fxCredits=service.listAll();

        return Mono.just(ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fxCredits));
    }

    @GetMapping("/{contractNumber}")
    public Mono<ResponseEntity<Credit>> getByPan(@PathVariable("contractNumber") String contractNumber){
        return service.getByContractNumber(contractNumber)
                .map(p->ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p)
                ); //Mono<ResponseEntity<CreditCard>>
    }

    @PostMapping
    public Mono<ResponseEntity<Credit>> register(@RequestBody Credit credit, final ServerHttpRequest req){
        return service.create(credit)
                .map(p->ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p)
                );
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Credit>> update(@PathVariable("id") String id,@RequestBody Credit credit){

        Mono<Credit> monoBody=Mono.just(credit);
        Mono<Credit> monoBD=service.getById(id);

        return monoBD
                .zipWith(monoBody, (bd,c)->{
                    bd.setId(id);
                    bd.setAmount(c.getAmount());
                    bd.setCapital(c.getCapital());
                    bd.setAmountInitial(c.getAmountInitial());
                    bd.setChargeDay(c.getChargeDay());
                    bd.setCommission(c.getCommission());
                    bd.setContractNumber(c.getContractNumber());
                    bd.setCustomer(c.getCustomer());
                    bd.setDateOperation(c.getDateOperation());
                    bd.setDebitor(c.isDebitor());
                    bd.setInterestRate(c.getInterestRate());
                    return bd;
                })
                .flatMap(service::update) //bd->service.modificar(bd)
                .map(a->ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(a))
                .defaultIfEmpty(new ResponseEntity<Credit>(HttpStatus.NOT_FOUND));
    }
}
