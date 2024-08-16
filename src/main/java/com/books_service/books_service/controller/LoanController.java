package com.books_service.books_service.controller;

import com.books_service.books_service.model.Loan;
import com.books_service.books_service.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping("/getAllLoans")
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/getLoanById/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {
        Loan loan = loanService.getLoanById(id);
        return ResponseEntity.ok(loan);
    }

    @PostMapping("/addNewLoan")
    public ResponseEntity<Loan> addNewLoan(@RequestBody Loan loan) {
        Loan savedLoan = loanService.addNewLoan(loan);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLoan);
    }

    @PutMapping("/updateReturnDate/{id}")
    public ResponseEntity<Loan> updateReturnDate(@PathVariable Long id, @RequestBody Date returnDate) {
        return loanService.updateReturnDate(id, returnDate);
    }

    @DeleteMapping("/deleteLoan/{id}")
    public ResponseEntity<String> deleteLoan(@PathVariable Long id) {
        return loanService.deleteLoan(id);
    }
}
