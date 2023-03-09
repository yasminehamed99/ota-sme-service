package com.example.otasmeservice.repository;

import com.example.otasmeservice.model.data.IRNSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRNSequenceRepo extends JpaRepository<IRNSequence,Long> {
    @Query("SELECT sequence+1 FROM IRNSequence WHERE taxNumber = ?1")
    Integer getNextIRNSequence(String taxNumber);
    @Query("FROM IRNSequence WHERE taxNumber = ?1")
    IRNSequence findByTinNumber(String taxNumber);

}
