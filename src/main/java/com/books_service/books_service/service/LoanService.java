package com.books_service.books_service.service;

import com.books_service.books_service.model.Book;
import com.books_service.books_service.model.Loan;
import com.books_service.books_service.repository.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with id: " + id));
    }

    public Loan addNewLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public ResponseEntity<Loan> updateReturnDate(Long id, Date returnDate) {
        Loan existingLoan = getLoanById(id);
        existingLoan.setReturnDate(returnDate);
        loanRepository.save(existingLoan);
        return new ResponseEntity<>(existingLoan, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteLoan(Long id) {
        Loan existingLoan = loanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with id: " + id));

        loanRepository.delete(existingLoan);
        return new ResponseEntity<>("Loan has been deleted", HttpStatus.OK);
    }
}
