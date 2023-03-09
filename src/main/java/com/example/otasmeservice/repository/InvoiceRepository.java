package com.example.otasmeservice.repository;


import com.example.otasmeservice.model.data.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("FROM Invoice WHERE invoiceUniqueIdentifier = ?1 AND invoiceNumber = ?2")
    Invoice findByUUIDAndNumber(String uuid, String invoiceNumber);
}
