package com.books_service.books_service.controller;

import com.books_service.books_service.model.Member;
import com.books_service.books_service.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Member>> getAllBooks(){
        List<Member> datas = memberService.getAllMembers();
        return new ResponseEntity(datas, HttpStatus.OK);
    }

    @GetMapping("/getMemberById/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id){
        Member data = memberService.getMemberById(id);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/addNewMember")
    public ResponseEntity<Member> addNewMember(@RequestBody Member member) {
        Member savedMember = memberService.addNewMember(member);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }

    @PutMapping("/updateMember/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member member){
        return memberService.updateMember(id,member);
    }

    @DeleteMapping("/deleteMember/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id){
        return memberService.deleteMember(id);
    }
}
