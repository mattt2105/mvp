package com.books_service.books_service;

import com.books_service.books_service.model.Loan;
import com.books_service.books_service.repository.LoanRepository;
import com.books_service.books_service.service.LoanService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanRepository loanRepository;

    private Loan loan;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loan = new Loan();
        loan.setLoanId(1L);
        loan.setLoanDate(new Date());
    }

    @Test
    void testGetAllLoans() {
        List<Loan> loans = new ArrayList<>();
        loans.add(loan);
        Mockito.when(loanRepository.findAll()).thenReturn(loans);

        List<Loan> result = loanService.getAllLoans();
        Assertions.assertEquals(1, result.size());
        verify(loanRepository, times(1)).findAll();
    }

    @Test
    void testGetLoanById() {
        Mockito.when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        Loan result = loanService.getLoanById(1L);
        Assertions.assertEquals(loan.getLoanId(), result.getLoanId());
        verify(loanRepository, times(1)).findById(1L);
    }

    @Test
    void testGetLoanByIdNotFound() {
        Mockito.when(loanRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            loanService.getLoanById(1L);
        });
        Assertions.assertEquals("Loan not found with id: 1", exception.getMessage());
    }

    @Test
    void testAddNewLoan() {
        Mockito.when(loanRepository.save(loan)).thenReturn(loan);

        Loan result = loanService.addNewLoan(loan);
        Assertions.assertEquals(loan.getLoanId(), result.getLoanId());
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    void testUpdateReturnDate() {
        Date returnDate = new Date();
        loan.setReturnDate(returnDate);
        Mockito.when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        Mockito.when(loanRepository.save(loan)).thenReturn(loan);

        ResponseEntity<Loan> result = loanService.updateReturnDate(1L, returnDate);
        Assertions.assertEquals(returnDate, result.getBody().getReturnDate());
        verify(loanRepository, times(1)).findById(1L);
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    void testDeleteLoan() {
        Mockito.when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        doNothing().when(loanRepository).delete(loan);

        ResponseEntity<String> result = loanService.deleteLoan(1L);
        Assertions.assertEquals("Loan has been deleted", result.getBody());
        verify(loanRepository, times(1)).delete(loan);
    }

    @Test
    void testDeleteLoanNotFound() {
        Mockito.when(loanRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            loanService.deleteLoan(1L);
        });
        Assertions.assertEquals("Loan not found with id: 1", exception.getMessage());
    }
}
