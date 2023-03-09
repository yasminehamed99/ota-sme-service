package com.example.otasmeservice.service;

import com.example.otasmeservice.model.data.IRNSequence;
import com.example.otasmeservice.repository.IRNSequenceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class InvoiceIdGenerator {

@Autowired
private IRNSequenceRepo irnSequenceRepo;
    public String generateInvoiceUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
//    public String incrementIRN(String taxNumber) {
//        return String.format("EIN%05d", taxNumber);
//

//    }
Random random = new Random();
    // Generates random integers 0 to 49
    int x = random.nextInt(50);
    public String incrementIRN() {

            return "EIN0000"+x;
    }
}
