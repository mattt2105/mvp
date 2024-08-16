package com.books_service.books_service;

import com.books_service.books_service.model.Member;
import com.books_service.books_service.repository.MemberRepository;
import com.books_service.books_service.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private Member member;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        member = new Member();
        member.setName("Jane Doe");
        member.setAddress("123 Main St");
        member.setPhone("1234567890");
        member.setEmail("jane.doe@example.com");
    }

    @Test
    public void testAddNewMember_Success() {
        Mockito.when(memberRepository.save(Mockito.any(Member.class))).thenReturn(member);

        Member savedMember = memberService.addNewMember(member);

        Assertions.assertNotNull(savedMember);
        Assertions.assertEquals("Jane Doe", savedMember.getName());
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    public void testGetMemberById_Success() {
        Mockito.when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        Member foundMember = memberService.getMemberById(1L);

        Assertions.assertNotNull(foundMember);
        Assertions.assertEquals("Jane Doe", foundMember.getName());
        verify(memberRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetMemberById_NotFound() {
        Mockito.when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            memberService.getMemberById(1L);
        });

        Assertions.assertEquals("Member not found with id: 1", exception.getMessage());
    }

    @Test
    public void testUpdateMember_Success() {
        Mockito.when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        Mockito.when(memberRepository.save(Mockito.any(Member.class))).thenReturn(member);

        Member updatedMember = new Member();
        updatedMember.setName("Jane Smith");
        updatedMember.setAddress("456 Main St");
        updatedMember.setPhone("0987654321");
        updatedMember.setEmail("jane.smith@example.com");

        ResponseEntity<Member> response = memberService.updateMember(1L, updatedMember);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Jane Smith", response.getBody().getName());
        verify(memberRepository, times(1)).save(Mockito.any(Member.class));
    }

    @Test
    public void testDeleteMember_Success() {
        Mockito.when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        ResponseEntity<String> response = memberService.deleteMember(1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("member has been deleted", response.getBody());
        verify(memberRepository, times(1)).delete(member);
    }

    @Test
    public void testDeleteMember_NotFound() {
        Mockito.when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = memberService.deleteMember(1L);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("member doesnt exist", response.getBody());
    }

    @Test
    public void testValidateMember_NameIsRequired() {
        member.setName(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            memberService.addNewMember(member);
        });

        Assertions.assertEquals("Name is required", exception.getMessage());
    }

    @Test
    public void testValidateMember_AddressIsRequired() {
        member.setAddress(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            memberService.addNewMember(member);
        });

        Assertions.assertEquals("Address is required", exception.getMessage());
    }

    @Test
    public void testValidateMember_PhoneIsRequired() {
        member.setPhone(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            memberService.addNewMember(member);
        });

        Assertions.assertEquals("Phone is required", exception.getMessage());
    }

    @Test
    public void testValidateMember_EmailIsRequired() {
        member.setEmail(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            memberService.addNewMember(member);
        });

        Assertions.assertEquals("Email is required", exception.getMessage());
    }
}
