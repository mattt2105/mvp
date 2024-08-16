package com.books_service.books_service.service;

import com.books_service.books_service.model.Member;
import com.books_service.books_service.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    public List<Member> getAllMembers(){
        List<Member> datas = memberRepository.findAll();
        return datas;
    }

    public Member getMemberById(Long id) {
        Member data = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + id));
        return data;
    }

    public Member addNewMember(Member member) {
        validateMember(member);
        return memberRepository.save(member);
    }

    public ResponseEntity<Member> updateMember(Long id, Member member){
        Optional<Member> existingdata = memberRepository.findById(id);
        if (existingdata.isPresent()){
            Member updateData = existingdata.get();

            updateData.setName(member.getName());
            updateData.setAddress(member.getAddress());
            updateData.setPhone(member.getPhone());
            updateData.setEmail(member.getEmail());
            memberRepository.save(updateData);

            return new ResponseEntity(updateData, HttpStatus.OK);
        }
        return new ResponseEntity("member doesnt exist", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteMember(Long id){
        Optional<Member> existingdata = memberRepository.findById(id);
        if (existingdata.isPresent()){
            memberRepository.delete(existingdata.get());

            return new ResponseEntity("member has been deleted",HttpStatus.OK);
        }
        return new ResponseEntity("member doesnt exist",HttpStatus.BAD_REQUEST);
    }

    private void validateMember(Member member) {
        if (member.getName() == null || member.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (member.getAddress()== null || member.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Address is required");
        }
        if (member.getPhone() == null || member.getPhone().isEmpty()) {
            throw new IllegalArgumentException("Phone is required");
        }
        if (member.getEmail() == null || member.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
    }
}
