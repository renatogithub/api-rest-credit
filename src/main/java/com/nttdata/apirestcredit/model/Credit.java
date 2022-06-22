package com.nttdata.apirestcredit.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nttdata.apirestcredit.dto.CustomerDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "credits")
public class Credit {

    @Id
    private String id;

    private double capital;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOperation = LocalDateTime.now();

    private String contractNumber;

    private double amountInitial;

    private double amount;

    private String chargeDay;

    private double commission;

    private double interestRate;

    private boolean debitor;

    private CustomerDTO customer;
}
